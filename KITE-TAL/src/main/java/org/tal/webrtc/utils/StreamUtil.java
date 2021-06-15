package org.tal.webrtc.utils;

import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class StreamUtil {
    public static String scriptsPath="/home/apple/Kite/KITE-TAL/scripts/";

    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public static String executeCommand(String command){
        try {
            Process pyP=Runtime.getRuntime().exec(command);
            BufferedReader br=new BufferedReader(new InputStreamReader(pyP.getInputStream()));
            StringBuilder sb=new StringBuilder();
            ArrayList<String> lines=new ArrayList();
            String line;
            while ((line=br.readLine())!=null){
                lines.add(line);
            }
            return lines.get(line.length()-1);
        }catch (IOException e){
            System.out.println(e.getMessage());
            return "error";
        }
    }

    public static String takeoutAudio(String videoPath) {
        String pyCommand="python3 "+scriptsPath+"take_out_audio.py "+videoPath;
        return executeCommand(pyCommand);
    }

    public static boolean audioWave(String audioPath){
        String pyCommand="python3 "+scriptsPath+"/draw_audio_wave.py "+audioPath;
        return "1".equalsIgnoreCase(executeCommand(pyCommand));
    }
}
