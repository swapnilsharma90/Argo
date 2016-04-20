package com.dd.argo.service;

import android.content.Context;
import android.os.AsyncTask;

import com.dd.argo.R;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

/**
 * Created by jkarkhanis on 31/03/16.
 */
public class ApiAiService {

    private AIConfiguration config;
    private AIRequest aiRequest;
    private AIDataService aiDataService;

    public ApiAiService(Context context) {
        config = new AIConfiguration(context.getResources().getString(R.string.access_token), AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiRequest = new AIRequest();
        aiDataService = new AIDataService(context, config);


    }

    public void sendRequest(String query, final AIListener listener) {
        aiRequest.setQuery(query);
        new AsyncTask<AIRequest, Void, AIResponse>() {
            @Override
            protected AIResponse doInBackground(AIRequest... requests) {
                final AIRequest request = requests[0];
                try {
                    final AIResponse response = aiDataService.request(aiRequest);
                    return response;
                } catch (AIServiceException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                if (aiResponse != null) {
                    listener.onResult(aiResponse);
                }
            }
        }.execute(aiRequest);
    }
}
