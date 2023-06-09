<template>
  <div>
    <div>
      이름 : {{ memberInfo.memberName }}
      <br />
      <br />
      [즐겨찾기]
      <br />
      <br />
      <ul
        v-for="favorite in memberInfo.memberFavorite"
        :key="favorite.content_id"
      >
        <li @click="setCampIdx(favorite.content_id)">
          {{ favorite.content_title }}
        </li>
      </ul>
      <br />
      <br />
      <br />
      <button @click="logout">로그아웃</button>
      <button @click="setPasswordChangeFlag">비밀번호 변경</button>
      <button @click="setDeactivateFlag">회원탈퇴</button>
    </div>

    <MemberDeactivateVue v-show="pageFlags.memberDeactivateFalg == true" />
    <MemberPasswordChangeVue
      v-show="pageFlags.memberPasswordChangeVue == true"
    />

    <CampDetail :content_id="campIdx" v-show="campIdx != -1" />
  </div>
</template>

<script>
import MemberDeactivateVue from "@/components/member/MemberDeactivate.vue";
import MemberPasswordChangeVue from "@/components/member/MemberPasswordChange.vue";
import CampDetail from "@/components/camp/CampDetail.vue";

import http from "@/util/http.js";

export default {
  data() {
    return {
      memberInfo: {
        memberName: "",
        memberFavorite: [],
      },
      campIdx: -1,
      pageFlags: {
        memberDeactivateFalg: false,
        memberPasswordChangeVue: false,
      },
    };
  },
  components: {
    MemberDeactivateVue,
    MemberPasswordChangeVue,
    CampDetail,
  },
  created() {
    if (this.$store.getters.getAccessToken == "") {
      if (confirm("로그인이 필요합니다"))
        location.href = "http://localhost:8080/";
      else location.href = "http://localhost:8080/";
    }

    this.memberInfo.memberName = this.$store.getters.getUserName;
    http.get(`favorites/${this.$store.getters.getUserIdx}`).then((res) => {
      console.log(res.data);
      for (let i = 0; i < res.data.length; i++) {
        let content_id = res.data[i].content_id;

        http.get(`/camp/content/${content_id}`).then((res) => {
          this.memberInfo.memberFavorite.push({
            content_id: content_id,
            content_title: res.data.title,
          });
        });
      }
    });
  },
  methods: {
    setCampIdx(campIdx) {
      this.campIdx = campIdx;
    },
    setDeactivateFlag() {
      this.pageFlags.memberDeactivateFalg = true;
      this.pageFlags.memberPasswordChangeVue = false;
    },
    setPasswordChangeFlag() {
      this.pageFlags.memberDeactivateFalg = false;
      this.pageFlags.memberPasswordChangeVue = true;
    },
    changePassword() {
      console.log("비밀번호 변경");
    },
    deactivate() {
      console.log("회원탈퇴요청");
    },
    logout() {
      http.delete(`/auth/logout/${this.$store.state.userIdx}`).then((res) => {
        console.log(res.data);
        if (res.data.message == "success") {
          console.log("a");
          this.$store.commit("SET_USER_NAME", "");
          this.$store.commit("SET_USER_IDX", -1);
          this.$store.commit("SET_ACCESS_TOKEN", "");
          this.$store.commit("SET_REFRESH_TOKEN", "");
          if (confirm("로그아웃 되었습니다."))
            window.location.href = "http://localhost:8080/";
        }
      });
    },
  },
};
</script>

<style></style>
