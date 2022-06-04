package pwa.Entities.Friends;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import pwa.Repositories.ProfileStorage;
import pwa.Repositories.RelationshipsStorage;
import pwa.Entities.Models.Relationships;
import pwa.Entities.Models.Status;

@Service
public class FriendsServiceDomain implements FriendsService {
	
	@Autowired
    RelationshipsStorage relationshipsStorage;
	
	@Autowired
    ProfileStorage profileStorage;
	
	public List<Relationships> getSubscribersList(Long targetId){
		return relationshipsStorage.findFollowers(targetId);
	}
	
	public List<Relationships> getSubscriptionsList(Long profileId){
		return relationshipsStorage.findSubscriptions(profileId);
	}
	
	public List<Relationships> getFriendsList(Long profileId){
		return relationshipsStorage.findFriends(profileId);
	}
	
	public void sendInvite(Long profileId, Long targetId) {
		relationshipsStorage.sendInvite(profileId, targetId);
	}
	
	public void acceptInvite(Long id) {
		relationshipsStorage.acceptInvite(id);
	}
	
	public void deleteFriend(Long profileId, Long targetId, Long id) {
		Relationships relationships = relationshipsStorage.findRelationships(id);
		if(relationships.getProfile_id() == profileId)
			relationshipsStorage.deleteFriend(profileId, targetId, id);
		if(relationships.getProfile_id() == targetId)
			relationshipsStorage.deleteFriend(targetId, profileId, id);
	}
	
	public void unsubscribe(Long id) {
		relationshipsStorage.unsubscribe(id);
	}

	@Override
	public String buttonText(Long profileId, Long targetId) {
		if(profileId != targetId) {
			Relationships relationship;
			try {
				relationship = relationshipsStorage.findRelationshipsByUsers(profileId, targetId);
				Status status = relationship.getStatus();
				if(status == Status.SUBSCRIBER)
					return "unsubscribe";
				if (status == Status.FRIEND)
					return "delete";
			} catch(EmptyResultDataAccessException e) {
				try {
					relationship = relationshipsStorage.findRelationshipsByUsers(targetId, profileId);
					Status status = relationship.getStatus();
					if(status == Status.SUBSCRIBER)
						return "add";
					if (status == Status.FRIEND)
						return "delete";
				} catch(EmptyResultDataAccessException e1) {
					return "add";
				}
			}
		} else 
			return "hide";
		return null;
	}

	@Override
	public int getAccesLevel(Long profileId, Long loginProfileId) {
		int accesLevel = 0;
		if(profileStorage.getRole(loginProfileId).equals("MODERATOR")) {
			return 5;
		}
		if(profileStorage.getRole(loginProfileId).equals("ADMIN")) {
			return 10;
		}
		if(profileId == loginProfileId)
			accesLevel = 2;
		else {
			try {
				Status status = relationshipsStorage.findRelationshipsByUsers(loginProfileId, profileId).getStatus();
				if(status == Status.SUBSCRIBER)
					accesLevel = 0;
				if(status == Status.FRIEND)
					accesLevel = 1;
			} catch(EmptyResultDataAccessException e) {
				try {
					Status status = relationshipsStorage.findRelationshipsByUsers(profileId, loginProfileId).getStatus();
					if(status == Status.SUBSCRIBER)
						accesLevel = 0;
					if(status == Status.FRIEND)
						accesLevel = 1;
				} catch(EmptyResultDataAccessException e1) {
					accesLevel = 0;
				}
			}
		}
		return accesLevel;
	}

	@Override
	public void changeRelationship(Long profileId, Long targetId) {
		Relationships relationship;
		try {
			relationship = relationshipsStorage.findRelationshipsByUsers(profileId, targetId);
			Status status = relationship.getStatus();
			if(status == Status.SUBSCRIBER)
				relationshipsStorage.unsubscribe(relationship.getId());
			if (status == Status.FRIEND)
				relationshipsStorage.deleteFriend(targetId, profileId, relationship.getId());
		} catch(EmptyResultDataAccessException e) {
			try {
				relationship = relationshipsStorage.findRelationshipsByUsers(targetId, profileId);
				Status status = relationship.getStatus();
				if(status == Status.SUBSCRIBER)
					relationshipsStorage.acceptInvite(relationship.getId());
				if (status == Status.FRIEND)
					relationshipsStorage.deleteFriend(targetId, profileId, relationship.getId());
			} catch(EmptyResultDataAccessException e1) {
				relationshipsStorage.sendInvite(profileId, targetId);
			}
		}
	}
}
