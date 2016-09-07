package com.eden.dao;

import java.util.LinkedList;
import java.util.List;

import com.eden.domain.Answer;
import com.eden.domain.AnswerEden;
import com.eden.domain.AskedExpert;
import com.eden.domain.Collection;
import com.eden.domain.CollectionContent;
import com.eden.domain.CollectionProblem;
import com.eden.domain.Comment;
import com.eden.domain.CommentEden;
import com.eden.domain.ContentEden;
import com.eden.domain.Expert;
import com.eden.domain.Problem;
import com.eden.domain.ProblemEden;
import com.eden.domain.User;

public interface PhoneDao {
	public List<ContentEden> loadContent();

	boolean addCollection(Collection c);

	public boolean comment(Comment c);

	public boolean registerSuccess(User user);

	public List<CommentEden> showComment(String contentId);

	public boolean retsetSuccess(User user);

	public boolean isCollection(Collection c);

	public boolean deleteCollection(Collection c);
	
	public List<CommentEden> getComment(String expertacount);

	public boolean sendSuccess(User user);

	public List<AnswerEden> showAnswer(String string);

	public List<ProblemEden> showProblem();
	
	public boolean setUserState(String state,String userId);
	
	public List<User> getFriends(String userId);

	public boolean addReply(Answer answer);

	public boolean sendProblem(Problem problem);

	public boolean isFriend(String account, String friendAccount);
    
	public List<CollectionContent>finCollectionContent(String userAccount);
	
	public List<CollectionProblem>finCollectionProblem(String userAccount);
     
	public String getToken(String userId);
	
	public String addToken(String userId,String token);

	public boolean addAskedExpert(AskedExpert expert);

	public Problem searchNewProblem(String userAccount);

	public LinkedList<Expert> addAsked(String account);
}
