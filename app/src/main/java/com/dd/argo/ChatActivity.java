package com.dd.argo;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.argo.adapter.ChatRecyclerViewAdapter;
import com.dd.argo.model.ChatItem;
import com.dd.argo.service.ApiAiService;

import java.util.ArrayList;

import ai.api.AIListener;
import ai.api.model.AIError;
import ai.api.model.AIResponse;

public class ChatActivity extends AppCompatActivity implements AIListener {

    private RecyclerView chatRecyclerView;
    private EditText chatBox;
    private Button submitButton;
    private ChatRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiAiService apiAiService;
    private Toolbar toolbar;
    private boolean hasEnteredFloor = false;
    private boolean hasEnteredTime = false;
    private boolean hasEnteredDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupUI(findViewById(R.id.activity_chat_parent_layout));
//        toolbar=(Toolbar)findViewById(R.id.app_toolbar);
//        setSupportActionBar(toolbar);
        chatRecyclerView = (RecyclerView) findViewById(R.id.activity_chat_recycler_view);
        chatBox = (EditText) findViewById(R.id.activity_chat_edit_text);
        submitButton = (Button) findViewById(R.id.activity_chat_button);
        mLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatRecyclerViewAdapter(getDataSet());
        chatRecyclerView.setAdapter(mAdapter);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });
        apiAiService = new ApiAiService(this);
        // TextView textView=(TextView)findViewById(R.id.layout_chat_list_item_textview);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "oxygen.ttf");
        // textView.setTypeface(typeface);
        chatBox.setTypeface(typeface);


    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ChatActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }


    public ArrayList<ChatItem> getDataSet() {
        ArrayList<ChatItem> itemList = new ArrayList<>();
        ChatItem chatItem = new ChatItem(getResources().getString(R.string.default_message), false);
        itemList.add(chatItem);
        return itemList;
    }

    public void sendChatMessage() {
        String userText = chatBox.getText().toString();
        if (!userText.isEmpty()) {
            mAdapter.addItem(new ChatItem(userText, true));
            int last_pos = mAdapter.getItemCount() - 1;
            mAdapter.setSelectedItem(last_pos);
            chatRecyclerView.scrollToPosition(last_pos);
            apiAiService.sendRequest(userText, this);
            chatBox.setText("");
        }

    }

    @Override
    public void onResult(AIResponse result) {

        Log.d("RESULT", result.getResult().getParameters().toString());
        if (result.getResult().getAction().toString().equals(getResources().getString(R.string.book_room_intent))) {

            if (!result.getResult().getStringParameter("Floor").isEmpty()) {
                hasEnteredFloor = true;
            }
            if (result.getResult().getDateParameter("date") != null) {
                hasEnteredDate = true;
            }
            if (result.getResult().getTimeParameter("time") != null) {
                hasEnteredTime = true;
            }
            if (result.getResult().getFulfillment().getSpeech().equals(getResources().getString(R.string.which_room))) {
                /**
                 * API call here
                 */
                Log.d("StringParameter", result.getResult().getStringParameter("Floor"));
                Log.d("DateParameter", result.getResult().getDateParameter("date").toString());
                Log.d("TimeParameter", result.getResult().getTimeParameter("time").toString());
                mAdapter.addItem(new ChatItem("Thy will be done", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            } else if (!hasEnteredFloor && hasEnteredTime && hasEnteredDate) {

                mAdapter.addItem(new ChatItem("Please enter the floor.", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            } else if (hasEnteredFloor && !hasEnteredTime && hasEnteredDate) {

                mAdapter.addItem(new ChatItem("Please enter the time.", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            } else if (hasEnteredFloor && hasEnteredTime && !hasEnteredDate) {

                mAdapter.addItem(new ChatItem("Please enter the date.", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            } else if (!hasEnteredFloor && !hasEnteredTime && hasEnteredDate) {

                mAdapter.addItem(new ChatItem("Please enter the floor and the time.", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            } else if (!hasEnteredFloor && hasEnteredTime && !hasEnteredDate) {

                mAdapter.addItem(new ChatItem("Please enter the floor and the date.", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            } else if (hasEnteredFloor && !hasEnteredTime && !hasEnteredDate) {

                mAdapter.addItem(new ChatItem("Please enter the time and the date.", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            } else if (!hasEnteredFloor && !hasEnteredTime && !hasEnteredDate) {

                mAdapter.addItem(new ChatItem("Please enter the floor, the time and the date.", false));
                int last_pos = mAdapter.getItemCount() - 1;
                mAdapter.setSelectedItem(last_pos);
                chatRecyclerView.scrollToPosition(last_pos);
            }


        }


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
