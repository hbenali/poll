/*
 * This file is part of the Meeds project (https://meeds.io/).
 * 
 * Copyright (C) 2022 Meeds Association contact@meeds.io
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package io.meeds.poll.utils;

import io.meeds.poll.entity.PollEntity;
import io.meeds.poll.entity.PollOptionEntity;
import io.meeds.poll.entity.PollVoteEntity;
import io.meeds.poll.model.Poll;
import io.meeds.poll.model.PollOption;
import io.meeds.poll.model.PollVote;

public class EntityMapper {

  private EntityMapper() {
  }

  public static Poll fromPollEntity(PollEntity pollEntity) {
    if (pollEntity == null) {
      return null;
    }
    return new Poll(pollEntity.getId(),
                    pollEntity.getQuestion(),
                    pollEntity.getCreatedDate(),
                    pollEntity.getEndDate(),
                    pollEntity.getCreatorId(),
                    pollEntity.getActivityId(),
                    pollEntity.getSpaceId());
  }

  public static PollEntity toPollEntity(Poll poll) {
    if (poll == null) {
      return null;
    }
    PollEntity pollEntity = new PollEntity();
    if (poll.getId() != 0) {
      pollEntity.setId(poll.getId());
    }
    pollEntity.setQuestion(poll.getQuestion());
    pollEntity.setCreatedDate(poll.getCreatedDate());
    pollEntity.setEndDate(poll.getEndDate());
    pollEntity.setActivityId(poll.getActivityId());
    pollEntity.setSpaceId(poll.getSpaceId());
    pollEntity.setCreatorId(poll.getCreatorId());
    return pollEntity;
  }

  public static PollOption fromPollOptionEntity(PollOptionEntity pollOptionEntity) {
    if (pollOptionEntity == null) {
      return null;
    }
    return new PollOption(pollOptionEntity.getId(), pollOptionEntity.getPollId(), pollOptionEntity.getDescription());
  }

  public static PollOptionEntity toPollOptionEntity(PollOption pollOption, Long pollEntityId) {
    if (pollOption == null) {
      return null;
    }
    PollOptionEntity pollOptionEntity = new PollOptionEntity();
    pollOptionEntity.setPollId(pollEntityId);
    pollOptionEntity.setDescription(pollOption.getDescription());
    return pollOptionEntity;
  }

  public static PollVote fromPollVoteEntity(PollVoteEntity pollVoteEntity) {
    if(pollVoteEntity == null) {
      return null;
    }
    return new PollVote(pollVoteEntity.getId(),
                        pollVoteEntity.getPollOptionId(),
                        pollVoteEntity.getVoterId(),
                        pollVoteEntity.getVoteDate());

  }

  public static PollVoteEntity toPollVoteEntity(PollVote pollVote) {
    if (pollVote == null) {
      return null;
    }
    PollVoteEntity pollVoteEntity = new PollVoteEntity();
    if (pollVote.getId() != 0) {
      pollVoteEntity.setId(pollVote.getId());
    }
    pollVoteEntity.setPollOptionId(pollVote.getPollOptionId());
    pollVoteEntity.setVoterId(pollVote.getVoterId());
    pollVoteEntity.setVoteDate(pollVote.getVoteDate());
    return pollVoteEntity;
  }
}
