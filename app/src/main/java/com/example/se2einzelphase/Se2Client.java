package com.example.se2einzelphase;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Se2Client extends AsyncTask<String, Void, String> {
    private Socket socket;

    public void createClient(int port, String hostname) throws IOException {
        socket = new Socket(hostname, port);
    }

    public void sendMessageFromClient(String message) throws IOException {
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

        outToServer.writeBytes(message + '\n');
    }

    public String retrieveDataFromClient() throws IOException {
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String output = inFromServer.readLine();
        return output;
    }

    public void closeClientSocket() throws IOException {
        socket.close();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if (params.length > 1) {
                Log.w("params", "Only First Param of SeClientTask is used");
            }
            createClient(53212, "se2-isys.aau.at");
            sendMessageFromClient(params[0]);
            return retrieveDataFromClient();

        } catch (IOException e) {
            Log.w("Socket Op", "Caught Exception while communicating with server", e);
        } finally {
            try {
                closeClientSocket();
            } catch (IOException e) {
                Log.w("Socket Close", "Error while closing socket", e);
            }
        }
        return "";
    }
}
