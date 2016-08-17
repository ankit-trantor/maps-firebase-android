package firbase.test.com.firebasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendBtn= (Button) findViewById(R.id.sendBtn);
        final EditText messageTxt= (EditText) findViewById(R.id.messageTxt);
        ListView messageList= (ListView) findViewById(R.id.messagesList);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(12,messageTxt.getText().toString(),"1202:23","Kraiba Semakula");
                myRef.push().setValue(message);
                messageTxt.setText("");

            }
        });
        final List <Message> messages =new ArrayList();
        final ArrayAdapter<Message> adapter = new ArrayAdapter<Message>(this,android.R.layout.two_line_list_item,messages){
            @Override
            public View getView(int position, View view , ViewGroup parent){
                view = getLayoutInflater().inflate(android.R.layout.two_line_list_item,parent,false);
                Message message =messages.get(position);
                ((TextView)view.findViewById(android.R.id.text1)).setText(message.getText());
                ((TextView)view.findViewById(android.R.id.text2)).setText(message.getUser());

                return view;
            }

        };
        messageList.setAdapter(adapter);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message =dataSnapshot.getValue(Message.class);
                messages.add(message);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
