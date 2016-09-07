package com.eden.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.eden.dao.PhoneDao;
import com.eden.dao.impl.PhoneDaoImpl;
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
import com.eden.service.PhoneService;

public class PhoneServiceImpl implements PhoneService{
	PhoneDao phone = new PhoneDaoImpl();

	@Override
	public List<ContentEden> loadContent() {
		
		return phone.loadContent();
	}

	@Override
	public boolean addCollection(Collection c) {
		// TODO Auto-generated method stub
		return phone.addCollection(c);
	}

	@Override
	public boolean comment(Comment c) {
		
		return phone.comment(c);
	}

	@Override
	public boolean registerSuccess(User user) {
		
		return phone.registerSuccess(user);
	}

	@Override
	public List<CommentEden> ShowComment(String contentId) {
		
		return phone.showComment(contentId);
	}

	@Override
	public boolean retsetSuccess(User user) {
		// TODO Auto-generated method stub
		return phone.retsetSuccess(user);
	}

	@Override
	public boolean isCollection(Collection c) {
		
		return phone.isCollection(c);
	}

	@Override
	public boolean deleteCollection(Collection c) {
		
		return phone.deleteCollection(c);
	}

	@Override
	public List<CommentEden> getComment(String expertacount) {
		// TODO Auto-generated method stub
		return phone.getComment(expertacount);
	}

	@Override
	public boolean sendSuccess(User user) {
		// TODO Auto-generated method stub
		return phone.sendSuccess(user);
	}

	@Override
	public List<AnswerEden> showAnswer(String string) {
		
		return phone.showAnswer(string);
	}

	@Override
	public List<ProblemEden> showProblem() {
		
		return phone.showProblem();
	}

	@Override
	public boolean setUserState(String state, String userId) {
		// TODO Auto-generated method stub
		return phone.setUserState(state, userId);
	}

	@Override
	public List<User> getFriends(String userId) {
		// TODO Auto-generated method stub
		return phone.getFriends(userId);
	}

	@Override
	public boolean addReply(Answer answer) {
		
		return phone.addReply(answer);
	}

	@Override
	public boolean addProblem(Problem problem) {
		
		return phone.sendProblem(problem);
	}

	@Override
	public boolean isFriend(String account,String friendAccount) {
		
		return phone.isFriend(account,friendAccount);
	}

	@Override
	public List<CollectionContent> finCollectionContent(String userAccount) {
		// TODO Auto-generated method stub
		return phone.finCollectionContent(userAccount);
	}

	@Override
	public List<CollectionProblem> finCollectionProblem(String userAccount) {
		// TODO Auto-generated method stub
		return phone.finCollectionProblem(userAccount);
	}

	@Override
	public String getToken(String userId) {
		// TODO Auto-generated method stub
		return phone.getToken(userId);
	}

	@Override
	public String addToken(String userId, String token) {
		// TODO Auto-generated method stub
		return phone.addToken(userId, token);
	}

	@Override
	public boolean addAskedExpert(AskedExpert expert) {
		
		return phone.addAskedExpert(expert);
	}

	@Override
	public Problem searchNewProblem(String userAccount) {
		
		return phone.searchNewProblem(userAccount);
	}

	@Override
	public LinkedList<Expert> searchAskedExpert(String account) {
		
		return phone.addAsked(account);
	}

}
