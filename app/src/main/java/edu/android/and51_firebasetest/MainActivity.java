package edu.android.and51_firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "test_tag"; // 로그확인

    private EditText editName, editAge, editId;
    private TextView textView;

    private FirebaseDatabase database;  // 데이터베이스에 접근할 수 있는 진입점 클래스
    private DatabaseReference myRef;    // 데이터베이스의 주소 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        textView = findViewById(R.id.textView);
        editId = findViewById(R.id.editId);

        database = FirebaseDatabase.getInstance();  // 현재 데이터 베이스를 접근할 수 있는 진입점
        // Person의 child로 이동
        // 데이터베이스의 루트 폴더 주소 값을 반환하는 메소드
        myRef = database.getReference("Person");

    } // end onCreate()


    // 데이터 저장 메소드
    private void writeNewPerson(String userId, String name, String age){

        Person person = new Person(userId, name, age);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = person.toMap();

        childUpdates.put("/" + userId, postValues);
        myRef.updateChildren(childUpdates);

    } // end writeNewPerson()


    // 데이터 저장 버튼
    public void onClickSaveData(View view) {
        String name = editName.getText().toString();
        String age = editAge.getText().toString();

        writeNewPerson(myRef.push().getKey(), name, age);

        editName.setText("");
        editAge.setText("");
    } // end saveData()

    // 데이터 가져오기 버튼
    public void onClickGetData(View view) {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder builder = new StringBuilder();
                Person person = null;
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                   person = snapShot.getValue(Person.class);
                    String[] info = {person.getId(), person.getName(), person.getAge()};
                    String result = info[0] + " - " + info[1] + " - " + info[2] + "\n";
                    builder.append(result);
                    if(person.getName().equals("gildong")){
                        Toast.makeText(MainActivity.this, "길동이 입니다", Toast.LENGTH_SHORT).show();
                    }

                }
                

                textView.setText(builder);
            } // end onDataChange()

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    } // end getData()


    public void onClickUpdateData(View view) {

        String id = editId.getText().toString();
        String updateName = editName.getText().toString();


        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("/" + id + "/name",updateName);
        myRef.updateChildren(updateMap);


    } // end onClickUpdateData()
} // end class MainActivity
