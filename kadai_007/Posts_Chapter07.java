package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null; // SELECTクエリの結果を取得


        // 投稿データ
        String[][] postList = {
        	{ "1003", "2023-02-08", "昨日の夜は徹夜でした…", "13" },
            { "1002", "2023-02-08", "お疲れ様です！", "12" },
            { "1003", "2023-02-09", "今日も頑張ります！", "18" },
            { "1001", "2023-02-09", "無理は禁物です！", "17" }, 
            { "1002", "2023-02-10", "明日から連休ですね！", "20" }
        };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "Ntsuki19931234"
            );

            System.out.println("データベース接続成功" + con.toString()); // 接続情報を出力

            // SQLクエリを準備
            String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
            statement = con.prepareStatement(sql);

            // リストの1行目から順番に読み込む
            int rowCnt = 0;
            for( int i = 0; i < postList.length; i++ ) {
                // SQLクエリの「?」部分をリストのデータに置き換え
                statement.setString(1, postList[i][0]); // user_id
                statement.setString(2, postList[i][1]); // posted_at
                statement.setString(3, postList[i][2]); // post_content
                statement.setString(4, postList[i][3]); // likes

                // SQLクエリを実行（DBMSに送信）
                rowCnt = statement.executeUpdate();
            }
                System.out.println("レコード追加を実行します");
                System.out.println( postList.length + "件のレコードが追加されました");
            
            
            // ユーザーIDが1002のレコードを検索
            String selectSQL = "SELECT * FROM posts WHERE user_id = 1002;"; 
            statement = con.prepareStatement(selectSQL);
            
            // SQLクエリを実行
            result = statement.executeQuery();
            System.out.println("ユーザーIDが1002のレコードを検索しました");
            
            // 必要なデータの抽出
            int count = 1; // 初期化
            while (result.next()) {
                String postedAt = result.getString("posted_at");
                String postContent = result.getString("post_content");
                int likes = result.getInt("likes");
            System.out.println(count + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likes);
            count++;
            }
            
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( result != null ) {
                try { result.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }

        }
    }
}
    
