package app.atividades.matc89.tutorialSQLite;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {

    private CommentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new CommentsDataSource(this);
        dataSource.open();

        List<Comment> values = dataSource.getAllComments();

        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, values);

    }

    public void onClick(View v) {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;

        switch (v.getId()) {

            case R.id.add:
                String[] comments = { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                comment = dataSource.createComment(comments[nextInt]);
                adapter.add(comment);
            break;

            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    dataSource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}

