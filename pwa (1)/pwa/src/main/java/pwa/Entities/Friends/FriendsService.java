package pwa.Entities.Friends;

import java.util.List;

import pwa.Entities.Models.Relationships;


public interface FriendsService {

	List<Relationships> getSubscribersList(Long targetId);

	List<Relationships> getSubscriptionsList(Long profileId);

	List<Relationships> getFriendsList(Long profileId);

	void sendInvite(Long profileId, Long targetId);

	void acceptInvite(Long id);

	void deleteFriend(Long profileId, Long targetId, Long id);

	void unsubscribe(Long id);

	String buttonText(Long profileId, Long targetId);

	int getAccesLevel(Long profileId, Long targetId);

	void changeRelationship(Long profileId, Long targetId);

}
