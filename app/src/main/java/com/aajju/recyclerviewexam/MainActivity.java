package com.aajju.recyclerviewexam;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MemoAdapter.OnMemoItemClickListener {

    private Api mApi;
    private MemoAdapter mMemoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApi = HttpHelper.getApi();
        // 모든 콜백 리스너(특정 이벤트가 실행시 수행되는 메소드) 구현하는 세가지 방법
        mMemoAdapter = new MemoAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mMemoAdapter);

        getMemoList();

        //메모 추가 버튼 클릭
        findViewById(R.id.main_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NewMemoActivity.class), 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK && data != null){
            String subject, content;
            subject = data.getStringExtra("subject");
            content = data.getStringExtra("content");
            addMemo(new Memo(subject, content, System.currentTimeMillis()));
            getMemoList();
        }

        if(requestCode == 2000 && resultCode == RESULT_OK && data != null){
            String subject, content;
            int id = data.getIntExtra("id", -1);
            subject = data.getStringExtra("subject");
            content = data.getStringExtra("content");

            updateMemo(id, new Memo(subject, content, System.currentTimeMillis()));
            getMemoList();

        }
    }

    private void getMemoList(){
        mApi.getMemoList().enqueue(new Callback<List<Memo>>() {
            @Override
            public void onResponse(Call<List<Memo>> call, Response<List<Memo>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "가져올 메모 리스트가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                mMemoAdapter.swapData(response.body());
            }

            @Override
            public void onFailure(Call<List<Memo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "메모 리스트 가져오기에 실패함", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void addMemo(final Memo memo){
        mApi.addMemo(memo.toString()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "메모추가에 싪패!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mMemoAdapter.addData(memo);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "OnFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void updateMemo(final int id, final Memo memo){
        mApi.updateMemo(memo.toString()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "메모 업데이트 실패", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                mMemoAdapter.updateData(memo.getId(), memo);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "OnFailure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteMemo(final int id){
        mApi.deleteMemo(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mMemoAdapter.deleteData(id);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMemoItemClicked(int position, final Memo memo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("메모 수정");
        builder.setMessage("메모를 수정하시겠습니까?");
        builder.setNegativeButton("취소" , null);
        builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, ModifyMemoActivity.class);
                intent.putExtra("memo", memo);
                startActivityForResult(intent, 2000);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //메모 삭제
    @Override
    public void onMemoItemLongClicked(final int position, final Memo memo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("메모 삭제");
        builder.setMessage("메모를 삭제하시겠습니까?");
        builder.setNegativeButton("취소" , null);
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteMemo(memo.getId());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
