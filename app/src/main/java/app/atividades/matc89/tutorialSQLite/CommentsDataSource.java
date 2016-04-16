package app.atividades.matc89.tutorialSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arleyprates on 4/16/16.
 */
public class CommentsDataSource {

    private SQLiteDatabase database;
    private MySQKLiteHelper dbHelper;
    private String[] allColumns = { MySQKLiteHelper.COLUMN_ID,
        MySQKLiteHelper.COLUMN_COMMENT };

    public CommentsDataSource(Context context) {
        dbHelper = new MySQKLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQKLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySQKLiteHelper.TABLE_COMMENTS, null, values);
        Cursor cursor = database.query(MySQKLiteHelper.TABLE_COMMENTS, allColumns, MySQKLiteHelper.COLUMN_ID +
        " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQKLiteHelper.TABLE_COMMENTS, MySQKLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQKLiteHelper.TABLE_COMMENTS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comment.add(comment)
            cursor.moveToNext();
        }

        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}
