package com.webapp.service.database.dao;

import com.webapp.model.Comment;

import java.util.List;

/**
 * This interface declares methods used to interact with table 't_comment' in the database.
 *
 * @author Juntao Peng (original creator)
 * @author Shangzhen Li (refactor)
 */
public interface CommentDao {

    /**
     * Get a list of comments in 't_comment' given size and verified.
     *
     * @param skip     The comment to skip
     * @param limit     The size of list
     * @param verified A flag whether comments are verified or not
     * @return A list of comments
     */
    List<Comment> listComment(int skip, int limit, boolean verified);

    /**
     * Get the total number of comments in 't_comment' given size and verified.
     *
     * @param verified A flag whether comments are verified or not
     * @return A int of total number
     */
    int queryNumberOfComments(boolean verified);

    /**
     * Query a list comments in 't_comment' given user id and verified.
     *
     * @param userId The related user id
     * @param verified A flag whether comments are verified or not
     * @return A list of comment.
    List<Comment> queryCommentByUserId(int userId, boolean verified);
     */

    /**
     * Query a comment in 't_comment' given id.
     *
     * @param commentId The id
     * @return A comment
     */
    Comment queryCommentById(int commentId);

    /**
     * Query comments in 't_comment' given building id.
     *
     * @param buildingId The building id
     * @return A List<Comment>
     */
    List<Comment> queryCommentByBuildingId(int buildingId);

    /**
     * Added a comment in 't_comment'
     *
     * @param comment The commend to add
     * @return True if succeeded, otherwise false
     */
    boolean addComment(Comment comment);

    /**
     * Delete a comment in 't_comment' given comment id
     *
     * @param commentId The commend id to delete
     * @return True if succeeded, otherwise false
     */
    boolean deleteComment(int commentId);

    /**
     * Update a comment in 't_comment'
     *
     * @param comment The commend to add
     * @return True if succeeded, otherwise false
     */
    boolean updateComment(Comment comment);
}
