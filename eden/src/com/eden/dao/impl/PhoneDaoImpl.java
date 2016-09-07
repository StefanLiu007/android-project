package com.eden.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.eden.dao.PhoneDao;
import com.eden.domain.Answer;
import com.eden.domain.AnswerEden;
import com.eden.domain.AskedExpert;
import com.eden.domain.Collection;
import com.eden.domain.CollectionContent;
import com.eden.domain.CollectionProblem;
import com.eden.domain.Comment;
import com.eden.domain.CommentEden;
import com.eden.domain.Content;
import com.eden.domain.ContentEden;
import com.eden.domain.Expert;
import com.eden.domain.Problem;
import com.eden.domain.ProblemEden;
import com.eden.domain.User;
import com.eden.util.DBHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class PhoneDaoImpl implements PhoneDao{

	@SuppressWarnings("resource")
	@Override
	public List<ContentEden> loadContent() {
		List<ContentEden> content = new ArrayList<ContentEden>();
		Connection conn =null;
		PreparedStatement pst=null,pst1=null;
		ResultSet res=null,res1 =null;
		try {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM content ";
			String sql1 = "SELECT COUNT(*) FROM view_comment WHERE CONTENT_ID=?";

			pst = (PreparedStatement) conn.prepareStatement(sql);
			res = pst.executeQuery();
			while(res.next()){
				String video = res.getString("CONTENT_VIDEO");
				String contentId = res.getString("CONTENT_ID");
				String userAccount = res.getString("USER_ACCOUNT");
				String text = res.getString("CONTENT_TEXT");
				String time = res.getString("CONTENT_LASTTIME");
				String title = res.getString("CONTENT_TITLE");
				String picture = res.getString("CONTENT_PICTURE");
				int count = 0;
				pst1 = (PreparedStatement) conn.prepareStatement(sql1);
				pst1.setString(1, contentId);
				res1 = pst1.executeQuery();
				if(res1.next()){
					count = res1.getInt(1);
				};



				Content c = new Content(userAccount,contentId,title,video, picture, text, count, time);
				ContentEden c1 = new ContentEden(c);
				content.add(c1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
			DBHelper.closeAll(conn, pst1, res1);
		}

		return content;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean addCollection(Collection c) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			if(c.getContentId() != null){
				String sql = "insert into collection(CONTENT_ID,COLLECTION_ACCOUNT,COLLECTION_TIME) values(?,?,?)";
				String sql1 = "select * from collection where CONTENT_ID=? and COLLECTION_ACCOUNT=?";
				pst = (PreparedStatement) conn.prepareStatement(sql1);
				pst.setString(1, c.getContentId());
				pst.setString(2, c.getUserAccount());
				rs = pst.executeQuery();
				if(rs.next()){
					return false;
				}else {
					pst = (PreparedStatement) conn.prepareStatement(sql);
					pst.setString(1, c.getContentId());
					pst.setString(2, c.getUserAccount());
					pst.setString(3, c.getTime());
					int b = pst.executeUpdate();
					if(b>0){
						return true;
					}
				}
			}else{
				String sql = "insert into collection(PROBLEM_ID,COLLECTION_ACCOUNT,COLLECTION_TIME) values(?,?,?)";
				String sql1 = "select * from collection where PROBLEM_ID=? and COLLECTION_ACCOUNT=?";
				pst = (PreparedStatement) conn.prepareStatement(sql1);
				pst.setString(1, c.getProblemId());
				pst.setString(2, c.getUserAccount());
				rs = pst.executeQuery();
				if(rs.next()){
					return false;
				}else {
					pst = (PreparedStatement) conn.prepareStatement(sql);
					pst.setString(1, c.getProblemId());
					pst.setString(2, c.getUserAccount());
					pst.setString(3, c.getTime());

					int b = pst.executeUpdate();
					if(b>0){
						return true;
					}
				}
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, null);
		}

		return false;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean comment(Comment c) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from content where CONTENT_ID=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
		    pst.setString(1, c.getContentId());
		    res = pst.executeQuery();
		    if(res.next()){
		    	pst = (PreparedStatement) conn.prepareStatement("insert into comment(CONTENT_ID,USER_ACCOUNT,COMMENT_CONTENT,COMMENT_REPLY,COMMENT_time) values(?,?,?,?,?)");
				pst.setString(1, c.getContentId());
				pst.setString(2, c.getUserAccount());
				pst.setString(3, c.getCommentContent());
				pst.setInt(4, c.getCommentReply());
				pst.setString(5, c.getTime());
				int a = pst.executeUpdate();
				if(a>0){
					return true;
				}
		    }else{
		    	pst = (PreparedStatement) conn.prepareStatement("insert into comment(EXPERT_ACCOUNT,USER_ACCOUNT,COMMENT_CONTENT,COMMENT_REPLY,COMMENT_time) values(?,?,?,?,?)");
				pst.setString(1, c.getContentId());
				pst.setString(2, c.getUserAccount());
				pst.setString(3, c.getCommentContent());
				pst.setInt(4, c.getCommentReply());
				pst.setString(5, c.getTime());
				int a = pst.executeUpdate();
				if(a>0){
					return true;
				}
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return false;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean registerSuccess(User user) {
		Connection conn=null;
		PreparedStatement ps=null;
		int i;
		ResultSet rs=null;
		try {
			conn = DBHelper.getConnection();
			String sql1 = "select * from user where USER_ACCOUNT=?";
			ps = (PreparedStatement) conn.prepareStatement(sql1);
			ps.setString(1, user.getUserAccount());
			rs = ps.executeQuery();
			while(rs.next()){
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");	
				if (user.getUserAccount().equals(USER_ACCOUNT)) {					
					return false;
				}else{
					return true;
				}
			}
			String sql2 = "insert into user(USER_ACCOUNT,USER_PASSWORD) values(?,?)";
			ps = (PreparedStatement) conn.prepareStatement(sql2);
			ps.setString(1, user.getUserAccount());
			ps.setString(2, user.getUserPassword());
			i = ps.executeUpdate();
			if(i>0){

				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, ps, rs);
		}
		return false;
	}

	@Override
	public List<CommentEden> showComment(String contentId) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet res = null;
		List<CommentEden> comments = new ArrayList<CommentEden>();
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("select * from view_comment where CONTENT_ID=?");
			pst.setString(1, contentId);
			res = pst.executeQuery();
			while(res.next()){
				String account = res.getString("USER_ACCOUNT");
				String commentContent = res.getString("COMMENT_CONTENT");
				String picture = res.getString("USER_ICON");
				String userName = res.getString("USER_NAME");
				String time = res.getString("COMMENT_time");
				int type = res.getInt("COMMENT_REPLY");
				Comment c = new Comment(account,commentContent, type, time);
				CommentEden cEden = new CommentEden(c, userName, picture);
				comments.add(cEden);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}

		return comments;
	}

	@Override
	public boolean retsetSuccess(User user) {
		// TODO Auto-generated method stub		
		Connection conn =null;
		PreparedStatement ps=null;
		int i;
		ResultSet rs=null;
		try {
			conn = DBHelper.getConnection();
			String sql1 = "select * from user where USER_ACCOUNT=?";
			ps = (PreparedStatement) conn.prepareStatement(sql1);
			ps.setString(1, user.getUserAccount());
			rs = ps.executeQuery();
			while(rs.next()){
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");	
				if (user.getUserAccount().equals(USER_ACCOUNT)) {					
					String sql2 = "update user set USER_PASSWORD=? where USER_ACCOUNT=?";
					ps = (PreparedStatement) conn.prepareStatement(sql2);
					ps.setString(2, user.getUserAccount());
					ps.setString(1, user.getUserPassword());
					i = ps.executeUpdate();
					if(i>0){				
						return true;
					}
				}else{
					return true;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, ps, rs);
		}
		return false;
	}

	@Override
	public boolean isCollection(Collection c) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql1 = "select * from collection where CONTENT_ID=? and COLLECTION_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql1);
			pst.setString(1, c.getContentId());
			pst.setString(2, c.getUserAccount());
			rs = pst.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, null);
		}

		return false;
	}

	@Override
	public boolean deleteCollection(Collection c) {
		Connection conn = null;
		PreparedStatement pst =null;

		try {
			conn = DBHelper.getConnection();
			String sql1 = "delete from collection where CONTENT_ID=? and COLLECTION_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql1);
			pst.setString(1, c.getContentId());
			pst.setString(2, c.getUserAccount());
			int a= pst.executeUpdate();
			if(a>0){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, null);
		}

		return false;
	}

	@Override
	public List<CommentEden> getComment(String expertacount) {
		List<CommentEden> list= new  ArrayList<>();
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql1 = "select * from view_expertcomment where EXPERT_ACCOUNT=? order by COMMENT_time";
			pst = (PreparedStatement) conn.prepareStatement(sql1);
			pst.setString(1, expertacount);
			rs = pst.executeQuery();
			while(rs.next()){
				String userAccount = rs.getString("USER_ACCOUNT");
				String commentContent = rs.getString("COMMENT_CONTENT");
				String commentTitle = rs.getString("COMMENT_TITLE");
				int commentReply = rs.getInt("COMMENT_REPLY");
				String expertAccount = rs.getString("EXPERT_ACCOUNT");
				String time= rs.getString("COMMENT_time");
				String name= rs.getString("USER_NICKNAME");
				String picture=rs.getString("USER_ICON");
				Comment comment= new Comment(userAccount, commentContent, commentTitle, expertAccount, commentReply, time);
				CommentEden commentEden= new CommentEden(comment, name, picture);
				list.add(commentEden);
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, null);
		}

		return null;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean sendSuccess(User user) {
		Connection conn=null;
		PreparedStatement ps=null;
		int i;
		ResultSet rs=null;
		try {
			conn = DBHelper.getConnection();
			String sql1 = "select * from user where USER_ACCOUNT=?";
			ps = (PreparedStatement) conn.prepareStatement(sql1);
			ps.setString(1, user.getUserMobile());
			rs = ps.executeQuery();
			while(rs.next()){


				String sql2 = "update user set USER_NICKNAME=?,USER_NAME=?,USER_SIGNATURE=?,USER_BIRTHDATE=?,USER_SEX=?,USER_SCHOOL=?,USER_MOBILE=?,USER_EMAIL=? where USER_ACCOUNT=?";
				ps = (PreparedStatement) conn.prepareStatement(sql2);
				ps.setString(1, user.getUserNickname());
				ps.setString(2, user.getUserName());
				ps.setString(3, user.getUserSignature());
				ps.setString(4, user.getUserBirthday());
				ps.setString(5, user.getUserSex());
				ps.setString(6, user.getUserSchool());
				ps.setString(7, user.getUserMobile());
				ps.setString(8, user.getUserMail());			
				ps.setString(9, user.getUserMobile());
				i = ps.executeUpdate();
				if(i>0){
					return true;
				}else{
					return false;
				}

			}   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, ps, rs);
		}
		return false;
	}

	@Override
	public List<AnswerEden> showAnswer(String string) {
		Connection conn =null;
		PreparedStatement pst = null;
		ResultSet res = null;
		List<AnswerEden> answerEden = new ArrayList<>();
		try {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM view_answer WHERE POBLEM_ID = ?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, string);
			res = pst.executeQuery();
			while(res.next()){
				String problemId = res.getString("POBLEM_ID");
				String answerAcount = res.getString("ANSWER_ACCOUNT");
				String name = res.getString("USER_NAME");
				String icon = res.getString("USER_ICON");
				String content = res.getString("ANSWER_CONTENT");
				String time = res.getString("ANSWER_LASTTIME");
				String replyAccount = res.getString("ANSWER_REPLYACCOUNT");
				int replyId = res.getInt("ANSWER_REPLY_ID");
				int id = res.getInt("ID");
				int type = res.getInt("TYPE");
				Answer an = new Answer(problemId, answerAcount, content, time, replyAccount,replyId, id, type);
				User use = new User(null, name, icon);
				AnswerEden eden = new AnswerEden(an, use);
				answerEden.add(eden);
			}
			return answerEden;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return null;
	}

	@Override
	public List<ProblemEden> showProblem() {
		Connection conn =null;
		PreparedStatement pst = null,pst1 = null;
		ResultSet res = null,res1 = null;
		List<ProblemEden> answerEden = new ArrayList<>();
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from view_problem_circle order by PROBLEM_LASTTIME";
			String sql1 = "SELECT COUNT(*) FROM view_answer WHERE POBLEM_ID=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			res = pst.executeQuery();
			while(res.next()){
				String PROBLEM_ID = res.getString("PROBLEM_ID");
				String USER_ACCOUNT = res.getString("USER_ACCOUNT");
				String USER_NAME = res.getString("USER_NAME");
				String USER_ICON = res.getString("USER_ICON");
				String PROBLEM_CONTENT = res.getString("PROBLEM_CONTENT");
				String PROBLEM_LASTTIME = res.getString("PROBLEM_LASTTIME");
				//int PROBLEM_ANSWERNUM = res.getInt("PROBLEM_ANSWERNUM");
				int count = 0;
				pst1 = (PreparedStatement) conn.prepareStatement(sql1);
				pst1.setString(1, PROBLEM_ID);
				res1 = pst1.executeQuery();
				if(res1.next()){
					count = res1.getInt(1);
				};
				
				User u = new User(USER_ACCOUNT, USER_NAME, USER_ICON);
				Problem p = new Problem(PROBLEM_ID, USER_ACCOUNT, PROBLEM_CONTENT, PROBLEM_LASTTIME, count);
				ProblemEden p1 = new ProblemEden(p, u);
				answerEden.add(p1);
			}
			return answerEden;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBHelper.closeAll(conn, pst1,res1);
			DBHelper.closeAll(conn, pst, res);
		}
		return null;
	}

	@Override
	public boolean setUserState(String state,String userId) {
		Connection conn =null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update user set USER_STATE=? where USER_ACCOUNT=? ";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, state);
			pst.setString(2, userId);
			int i=pst.executeUpdate();
			if(i!=0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBHelper.closeAll(conn, pst, null);
		}
		return false;
	}

	@Override
	public List<User> getFriends(String userId) {
		List<User> friends = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql="select * from view_friend where USER_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
				String FRIEND_ACCOUNT = rs.getString("FRIEND_ACCOUNT");
				String USER_NICKNAME = rs.getString("USER_NICKNAME");
				String USER_SIGNATURE = rs.getString("USER_SIGNATURE");
				String USER_BIRTHDATE = rs.getString("USER_BIRTHDATE");
				String USER_SEX = rs.getString("USER_SEX");
				String USER_SCHOOL = rs.getString("USER_SCHOOL");
				String USER_MOBILE = rs.getString("USER_MOBILE");
				String USER_EMAIL = rs.getString("USER_EMAIL");
				String USER_ICON = rs.getString("USER_ICON");
				String USER_STATE=rs.getString("USER_STATE");
				User user= new User(FRIEND_ACCOUNT, USER_NICKNAME, USER_SIGNATURE, USER_BIRTHDATE, USER_SEX, USER_SCHOOL, USER_MOBILE, USER_EMAIL, USER_ICON, USER_STATE);
				friends.add(user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, rs);
		}
		return friends;
	}

	@SuppressWarnings("null")
	@Override
	public boolean addReply(Answer answer) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			if(answer.getType() == 1){
				String sql = "insert into answer(POBLEM_ID,ANSWER_CONTENT,ANSWER_LASTTIME,ANSWER_ACCOUNT,ANSWER_REPLYACCOUNT,ANSWER_REPLY_ID,TYPE) values(?,?,?,?,?,?,?)";
				pst = (PreparedStatement) conn.prepareStatement(sql);
				pst.setString(1, answer.getProblemId());
				pst.setString(2, answer.getContent());
				pst.setString(3, answer.getTime());
				pst.setString(4, answer.getAnswerAccout());
				pst.setString(5, answer.getAnswerReplyAccount());
				pst.setInt(6, answer.getReplyId());
				pst.setInt(7, answer.getType());
				int a = pst.executeUpdate();
				if(a>0){
					return true;
				}
			}else{
				String sql1 = "insert into answer(POBLEM_ID,ANSWER_CONTENT,ANSWER_LASTTIME,ANSWER_ACCOUNT,TYPE) values(?,?,?,?,?)";
				pst = (PreparedStatement) conn.prepareStatement(sql1);
				pst.setString(1, answer.getProblemId());
				pst.setString(2, answer.getContent());
				pst.setString(3, answer.getTime());
				pst.setString(4, answer.getAnswerAccout());
				pst.setInt(5, answer.getType());
				int a = pst.executeUpdate();
				if(a>0){
					return true;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean sendProblem(Problem problem) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			String sql = "insert into problem(PROBLEM_ID,USER_ACCOUNT,PROBLEM_CONTENT,PROBLEM_LASTTIME,PROBLEM_ANSWERNUM) values(?,?,?,?,?)";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, problem.getProblemID());
			pst.setString(2, problem.getUserAccou());
			pst.setString(3, problem.getProblemContent());
			pst.setString(4, problem.getProblemLastTime());
			pst.setInt(5, problem.getProblemAnswerNum());
			int a = pst.executeUpdate();
			if(a>0){
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isFriend(String account,String friendAccount) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			String sql1 = "select * from Friend where USER_ACCOUNT=? and FRIEND_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql1);
			pst.setString(1, account);
			pst.setString(2, friendAccount);
			rs = pst.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, null);
		}

		return false;
	}

	@Override
	public List<CollectionContent> finCollectionContent(String userAccount) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet res = null;
		List<CollectionContent> contents=new ArrayList<>();
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from view_collection_content where COLLECTION_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			res = pst.executeQuery();
			while(res.next()){
				Content content=new Content();
				content.setContentId(res.getString("CONTENT_ID"));
				content.setContentTitle(res.getString("CONTENT_TITLE"));
				content.setContentText(res.getString("CONTENT_TEXT"));
				content.setContentPicture(res.getString("CONTENT_PICTURE"));
				content.setContentVideo(res.getString("CONTENT_VIDEO"));
				content.setThumbUpNum(res.getInt("THUMBUP_NUM"));
				content.setContentLastime(res.getString("CONTENT_LASTTIME"));
				String time=res.getString("COLLECTION_TIME");
				CollectionContent collectionContent=new CollectionContent(content, time);
				contents.add(collectionContent);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return contents;
	}

	@Override
	public List<CollectionProblem> finCollectionProblem(String userAccount) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet res = null;
		List<CollectionProblem> problems=new ArrayList<>();
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from view_collection_problem where COLLECTION_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, userAccount);
			res = pst.executeQuery();
			while(res.next()){
				String PROBLEM_ID=res.getString("PROBLEM_ID");
				String USER_ACCOUNT=res.getString("USER_ACCOUNT");
				String PROBLEM_CONTENT=res.getString("PROBLEM_CONTENT");
				String PROBLEM_IMAGE=res.getString("PROBLEM_IMAGE");
				String PROBLEM_LASTTIME=res.getString("PROBLEM_LASTTIME");
				int PROBLEM_ANSWERNUM=res.getInt("PROBLEM_ANSWERNUM");
				Problem problem=new Problem(PROBLEM_ID, USER_ACCOUNT, PROBLEM_CONTENT,PROBLEM_IMAGE, PROBLEM_LASTTIME, PROBLEM_ANSWERNUM);
				User user= new User();
				user.setUserAccount(USER_ACCOUNT);
				user.setUserBirthday(res.getString("USER_BIRTHDATE"));
				user.setUserNickname(res.getString("USER_NICKNAME"));
				user.setUserSignature(res.getString("USER_SIGNATURE")); 
				user.setUserSex(res.getString("USER_SEX"));
				user.setUserSchool(res.getString("USER_SCHOOL"));
				user.setUserMobile(res.getString("USER_MOBILE"));
				user.setUserMail(res.getString("USER_EMAIL"));
				user.setUserIcon(res.getString("USER_ICON"));
				user.setUserState(res.getString("USER_STATE"));
				String time=res.getString("COLLECTION_TIME");
				ProblemEden problemEden= new ProblemEden(problem, user);
				CollectionProblem collectionProblem= new CollectionProblem(problemEden, time);
				problems.add(collectionProblem);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return problems;
	}

	@Override
	public String getToken(String userId) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet res = null;
		String token=null;
		try {
			conn = DBHelper.getConnection();
			String sql = "select USER_TOKEN from user where USER_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, userId);
			res = pst.executeQuery();
			if(res.next()){
				token= res.getString("USER_TOKEN");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return token;
	}

	@Override
	public String addToken(String userId, String token) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet res = null;
		String newtoken=null;
		try {
			conn = DBHelper.getConnection();
			String sql = "update user set USER_TOKEN=? where USER_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, token);
			pst.setString(2, userId);
			int i = pst.executeUpdate();
			if(i>0){
				newtoken=getToken(userId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return newtoken;
	}

	@Override
	public boolean addAskedExpert(AskedExpert expert) {
		Connection conn = null;
		PreparedStatement pst =null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("insert into ask_expert(USER_ACCOUNT,EXPERT_ACCOUNT) values(?,?)");
			pst.setString(1, expert.getUserAccount());
			pst.setString(2, expert.getExpertAccount());
			int a = pst.executeUpdate();
			if(a>0){
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, null);
		}
		return false;
	}

	@Override
	public Problem searchNewProblem(String userAccount) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet res = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("select * from problem where USER_ACCOUNT = ?");
			pst.setString(1, userAccount);
			res = pst.executeQuery();
			if(res.last()){
				String content =  res.getString("PROBLEM_CONTENT");
			
				String time  = res.getString("PROBLEM_LASTTIME");
				Problem p = new Problem(content, time);
				return p;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return null;
	}

	@Override
	public LinkedList<Expert> addAsked(String account) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet res = null;
		LinkedList<Expert> list = new LinkedList();
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("select * from view_askedexpert where USER_ACCOUNT = ?");
			pst.setString(1, account);
			res = pst.executeQuery();
			while(res.next()){
				String account1=res.getString("EXPERT_ACCOUNT");
				String name=res.getString("EXPERT_NAME");
				String sex=res.getString("EXPERT_SEX");
				String state=res.getString("EXPERT_STATE");
				String introduce=res.getString("EXPERT_INTRODUCE");
				int pv=res.getInt("EXPERT_PV");
				String address=res.getString("EXPERT_ADDRESS");
				String icon=res.getString("ERTERT_ICON");
				String email=res.getString("ERTERT_EMAIL");
				Expert e=new Expert(account1, name, sex, state, introduce, pv, address, icon, email);
				list.add(e); 
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, res);
		}
		return null;
	}
}
