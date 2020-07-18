package com.webapp.service.database.dao.impl;

import com.webapp.model.Comment;
import com.webapp.service.database.DatabaseService;
import com.webapp.service.database.dao.CommentDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements CommentDao interface to interact with table 't_comment' in the database.
 *
 * @author Juntao Peng (original creator)
 * @author Shangzhen Li (refactor)
 */
public class CommentDaoImpl extends DatabaseService implements CommentDao {

    public CommentDaoImpl() {
    }

    @Override
    public List<Comment> listComment(int skip, int limit, boolean verified) {
        List<Comment> commentList = new ArrayList<Comment>();
        String sql = "SELECT * FROM t_comment WHERE verified = ? LIMIT ?, ?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, verified);
            preparedStatement.setInt(2, skip);
            preparedStatement.setInt(3, limit);
            System.out.print(preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setBuildingId(rs.getInt("building_id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setDate(rs.getTimestamp("date").getTime());
                comment.setContent(rs.getString("content"));
                comment.setVerified(rs.getBoolean("verified"));
                commentList.add(comment);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
        } finally {
            closeConnection(connection);
        }
        return commentList;
    }

    @Override
    public int queryNumberOfComments(boolean verified) {
        String sql = "SELECT COUNT(*) FROM t_comment where verified = ?";
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, verified);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    //  @Override
//  public List<Comment> queryCommentByUserId(int userId, boolean verified) {
//    List<Comment> commentList = new ArrayList<>();
//    String sql = "SELECT * FROM t_comment WHERE userId = ? AND verified=? ";
//    Connection connection = getConnection();
//    try {
//      PreparedStatement preparedStatement = connection.prepareStatement(sql);
//      preparedStatement.setInt(1, userId);
//      preparedStatement.setBoolean(2, verified);
//      ResultSet rs = preparedStatement.executeQuery();
//      while (rs.next()) {
//        Comment comment = new Comment();
//        comment.setId(rs.getInt("id"));
//        comment.setUserId(rs.getInt("userId"));
//        comment.setDate(rs.getLong("date"));
//          comment.setContent(rs.getString("content"));
//          comment.setVerified(rs.getBoolean("verified"));
//          commentList.add(comment);
//      }
//    } catch (SQLException sqlException) {
//      sqlException.printStackTrace(System.err);
//    } finally {
//      closeConnection(connection);
//    }
//    return commentList;
//  }

    @Override
    public Comment queryCommentById(int commentId) {
        String sql = "SELECT * FROM t_comment WHERE id=?";
        Connection connection = getConnection();
        Comment comment = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, commentId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setBuildingId(rs.getInt("building_id"));
                comment.setDate(rs.getTimestamp("date").getTime());
                comment.setContent(rs.getString("content"));
                comment.setVerified(rs.getBoolean("verified"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
        } finally {
            closeConnection(connection);
        }
        return comment;
    }

    @Override
    public List<Comment> queryCommentByBuildingId(int buildingId) {
        String sql = "SELECT * FROM t_comment WHERE building_id = ?";
        Connection connection = getConnection();
        List<Comment> commentList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, buildingId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setBuildingId(rs.getInt("building_id"));
                comment.setDate(rs.getTimestamp("date").getTime());
                comment.setContent(rs.getString("content"));
                comment.setVerified(rs.getBoolean("verified"));
                commentList.add(comment);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
        } finally {
            closeConnection(connection);
        }
        return commentList;
    }

    @Override
    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO t_comment(user_id,building_id,date,content) VALUES (?,?,?,?)";
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getBuildingId());
            preparedStatement.setTimestamp(3, new Timestamp(comment.getDate()));
            preparedStatement.setString(4, comment.getContent());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
        } finally {
            closeConnection(connection);
        }
        return 0 != result;
    }

    @Override
    public boolean deleteComment(int commentId) {
        String sql = "DELETE FROM t_comment WHERE id=?";
        Connection connection = getConnection();
        boolean result;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            result = false;
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean updateComment(Comment comment) {
        String sql = "UPDATE t_comment SET user_id = ?, building_id = ?, date = ?, content = ?, verified = ? WHERE id=?";
        Connection connection = getConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getBuildingId());
            preparedStatement.setTimestamp(3, new Timestamp(comment.getDate()));
            preparedStatement.setString(4, comment.getContent());
            preparedStatement.setBoolean(5, comment.isVerified());
            preparedStatement.setInt(6, comment.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
        } finally {
            closeConnection(connection);
        }
        return 0 != result;
    }
}
