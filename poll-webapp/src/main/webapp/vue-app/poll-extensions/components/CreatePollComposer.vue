<!--
This file is part of the Meeds project (https://meeds.io/).
 
Copyright (C) 2022 Meeds Association contact@meeds.io
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.
 
You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
-->
<template>
  <div>
    <div class="actionItem" @click="openCreatePollDrawer">
      <div class="actionItemIcon">
        <div class="createPollComposerIcon"></div>
      </div>
      <div class="actionItemDescription">
        <div :class="`actionLabel ${createdPollIcon}`"> {{ pollActionLabel }} </div>
        <div class="actionDescription">
          <p>{{ pollActionDescription }}</p>
        </div>
      </div>
    </div>
    <create-poll-drawer
      ref="createPollDrawer"
      :saved-poll="savedPoll"
      @poll-created="createPoll" />
  </div>
</template>
<script>
export default {
  data() {
    return {
      pollAction: 'create',
      savedPoll: {}
    };
  },
  props: {
    activityId: {
      type: String,
      default: null,
    },
    message: {
      type: String,
      default: null,
    },
    maxMessageLength: {
      type: Number,
      default: 0,
    },
    templateParams: {
      type: Object,
      default: null,
    },
    files: {
      type: Array,
      default: null,
    },
    activityType: {
      type: Array,
      default: null,
    },
  },
  computed: {
    pollActionLabel() {
      return this.$t(`composer.poll.${this.pollAction}.drawer.label`);
    },
    pollActionDescription() {
      return this.$t(`composer.poll.${this.pollAction}.drawer.description`);
    },
    createdPollIcon() {
      return this.pollAction === 'update' ? 'createdPollIcon' : '';
    }
  },
  created() {
    document.addEventListener('post-activity', event => {
      this.postPoll(event.detail);
    });
    document.addEventListener('message-composer-opened', () => {
      if (this.pollAction === 'update') {
        this.activityType.push('poll');
        document.dispatchEvent(new CustomEvent('activity-composer-edited'));
      }
    });
  },
  methods: {
    openCreatePollDrawer() {
      this.$refs.createPollDrawer.openDrawer();
    },
    createPoll(poll) {
      Object.assign(this.savedPoll, poll);
      this.pollAction = 'update';
      this.activityType.push('poll');
      document.dispatchEvent(new CustomEvent('activity-composer-edited'));
    },
    postPoll(message) {
      const poll = {
        question: this.savedPoll.question,
        options: this.savedPoll.options.filter(option => option.data != null && option.data !== '')
          .map(option => {
            return {
              description: option.data,
            };
          }),
        duration: this.savedPoll.duration,
        message: message,
        files: this.files
      };
      this.$pollService.postPoll(poll, eXo.env.portal.spaceId)
        .then(() => {
          document.dispatchEvent(new CustomEvent('activity-created', {detail: this.activityId}));
          this.pollAction = 'create';
          this.savedPoll = {};
        })
        .catch(error => {
          console.error(`Error when posting message: ${error}`);
        })
        .finally(() => {
          document.dispatchEvent(new CustomEvent('activity-composer-closed'));
        });
    }
  },
};
</script> 
