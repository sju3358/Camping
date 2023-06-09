<template>
  <div class="login-info">
    <input type="text" v-model="memberId" placeholder="아이디" />
    <br />
    <input type="password" v-model="memberPassword" placeholder="비밀번호" />
    <br />

    <button @click.prevent="login">로그인</button>

    <input type="checkbox" v-bind:checked="rememberId" v-model="rememberId" />
    <span>ID저장</span>

    <br />

    <img
      src="@/assets/button_naver_login.png"
      v-on:click="naverLogin"
      width="200px"
    />

    <br />

    <button @click.prevent="findMemberPassword">비밀번호 찾기</button>
    <button @click.prevent="registerButton">회원가입</button>
  </div>
</template>

<script>
import http from "@/util/http.js";
import axios from "axios";
export default {
  data() {
    return {
      memberId: "",
      memberPassword: "",
      rememberId: false,
      data: null,
    };
  },
  created() {
    if (this.$store.getters.getAccessToken != "") {
      if (confirm("이미 로그인 중입니다."))
        location.href = "http://localhost:8080/camping";
      return;
    }

    this.rememberId = this.$cookies.get("rememberId") == "true" ? true : false;
    this.memberId = this.$cookies.get("memberId");
    if (this.memberId == "null") this.memberId = "";

    let code = this.$route.query.code;
    let state = this.$route.query.state;

    if (code != undefined) {
      const data = {
        code: code,
        state: state,
      };

      axios
        .post("http://localhost:9000/camping/naver/auth", data)
        .then((res) => {
          if (res.data.message == "fail") alert(res.data.error);
          else {
            this.$store.commit("SET_USER_NAME", res.data.memberName);
            this.$store.commit("SET_USER_IDX", res.data.memberIdx);
            this.$store.commit("SET_ACCESS_TOKEN", res.data.accessToken);
            this.$store.commit("SET_REFRESH_TOKEN", res.data.refreshToken);
            if (confirm("로그인되었습니다."))
              location.href = "http://localhost:8080/camping";
          }
        });
    }
  },
  methods: {
    naverLogin() {
      location.href =
        "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=0jXgPVDyLQu_ekRssB20&state=STATE_STRING&redirect_uri=http://localhost:8080/";
    },
    login() {
      if (this.rememberId == true) this.$cookies.set("memberId", this.memberId);
      else this.$cookies.set("memberId", "");

      this.$cookies.set("rememberId", this.rememberId);

      const data = {
        id: this.memberId,
        pwd: this.memberPassword,
      };
      http.post("/auth/login", data).then((res) => {
        console.log(res.data);
        if (res.data.message == "fail") alert(res.data.error);
        else {
          this.$store.commit("SET_USER_NAME", res.data.memberName);
          this.$store.commit("SET_USER_IDX", res.data.memberIdx);
          this.$store.commit("SET_ACCESS_TOKEN", res.data.accessToken);
          this.$store.commit("SET_REFRESH_TOKEN", res.data.refreshToken);
          if (confirm("로그인되었습니다"))
            window.location.href = "http://localhost:8080/camping";
        }
      });
    },
    registerButton() {
      console.log("회원가입버튼클릭");
      this.$emit("register-button-click", true);
    },
    findMemberPassword() {
      console.log("비밀번호 찾기 요청");
      this.$emit("findPassword-button-click", true);
    },
  },
};
</script>

<style></style>
