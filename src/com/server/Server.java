package com.server;

import com.common.Protocol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by sist on 2016-02-02.
 */
public class Server implements Runnable {
    /*
        1.클라이언트: 연결요청(Socket)
        2.서버: 연결요청에 응답(ServerSocket, accept())
        3.서버: 연결수락(Scoket 생성됨.)
        4.서버와 클라이언트간 통신함.(1번과 3번 Socket간)
     */

    /*
        1.서버자체도 스레드임.
        2. 그안에 각 클라이언트 연결요청에 응답하는 각각의 작업 스레드도 필요함.
        아래 함수는 스레드 병렬 처리가 필요함.
        - accept(), connect(), read(), write()
     */

    //클라이언트 접속 대기하다 접속시 처리함.
    ServerSocket serverSocket;
    //클라이언트 정보 저장하는 객체(GClient) 필요
    Vector<GClient> vectorGClient=new Vector<GClient>();

    //포트 번호
    private final int PORT=3030;

    public static void main(String[] args){
        Server server=new Server();
        new Thread(server).start();
    }

    //서버스레드 생성자
    public Server(){
        try{
            serverSocket=new ServerSocket(PORT);
            //서버소켓 생성후엔 클라이언트로부터의 연결요청을 기다림(listen()).
            System.out.println("서버 시작됨!");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    //서버 실제 구동내용.
    @Override
    public void run() {
        try{
            while(true){
                //클라이언트 접속시 새로운 소켓이 생성되고 이후 이것으로 통신함.
                Socket socket=serverSocket.accept();
                //클라이언트 정보 저장하는 객체(스레드) 생성
                GClient gClient=new GClient(socket);
                //클-서(각기 스레드)간 소켯 통신시작하라.
                gClient.start();
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    //클라이언트와 서버간 통신시 사용되는 객체
    class GClient extends Thread{
        String id,name;
        //통신시엔 소켓 필요.
        Socket socket;
        //클라이언트 데이터 읽어들이고 스트림 객체 필요
        BufferedReader bufferedReader;
        //서버측 데이터를 클라이언트로 출력하는 스트림 객체 필요.
        OutputStream outputStream;

        //서버소켓으로부터 생성된 소켓(Socket -위에서 3번임)을 받음.
        public GClient(Socket socket){
            try{
                this.socket=socket;
                //클라이언트로 데이터를 보내는 스트림 객체
                outputStream=socket.getOutputStream();
                //클라이언트로부터 데이터를 받는 스트림 객체
                bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));


            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        //accept()이후 실행됨.
        @Override
        public void run() {
            try{
                while(true){
                    //클라이언트로부터 데이터를 받아들임.
                    String message=bufferedReader.readLine();
                    System.out.println("클라이언트로부터의 메시지: " +message);

                    //받아들인 데이터 파싱.
                    StringTokenizer stringTokenizer=new StringTokenizer(message, "||");
                    int protocol=Integer.parseInt(stringTokenizer.nextToken());

                    switch (protocol){
                        //최초 로그인 요청.
                        case Protocol.LOGIN: {
                            id=stringTokenizer.nextToken();
                            name=stringTokenizer.nextToken();

                            messageAll(Protocol.LOGIN +"||"+ id +"||" +name);

                            //요청한 헤당 클라이언트 저장
                            vectorGClient.addElement(this);

                            messageTo(Protocol.WAITROOMLOGIN +"||"+ id);

                            //저장된 클라이언트의 모든 정보를 보냄
                            for(GClient gClient:vectorGClient){
                                messageTo(Protocol.LOGIN +"||"+ gClient.id +"||"+ gClient.name);
                            }
                        }
                        break;
                    }
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        //클라이언트들에게로 전부 다 메시지 전송.
        public synchronized void messageAll(String message){
            try{
                for(GClient gClient:vectorGClient){
                    gClient.messageTo(message);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        //각 클라이언트로 개별 전송.
        public synchronized void messageTo(String message){
            try{
                outputStream.write((message +"\n").getBytes());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
