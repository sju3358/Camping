<template>
  <div>
    <br />
    <input type="password" v-model="curPassword" placeholder="현재 비밀번호" />
    <br />
    <input
      type="password"
      v-model="newPassword"
      placeholder="새로운 비밀번호"
    />
    <br />
    <input
      type="password"
      v-model="newPasswordRepeat"
      placeholder="새로운 비밀번호 확인"
    />
    <br />
    <button @click="changePassword">확인</button>
  </div>
</template>

<script>
import https from "@/util/http.js";
export default {
  data() {
    return {
      curPassword: "",
      newPassword: "",
      newPasswordRepeat: "",
    };
  },
  methods: {
    changePassword() {
      const data = {
        memberIdx: this.$store.state.userIdx,
        curPassword: this.curPassword,
        newPassword: this.newPassword,
        newPasswordRepeat: this.newPasswordRepeat,
      };

      console.log(data);
      https.put("/info/change-password", data).then((res) => {
        if (res.data.message == "success") {
          this.$store.commit("SET_USER_NAME", "");
          this.$store.commit("SET_USER_IDX", -1);
          this.$store.commit("SET_ACCESS_TOKEN", "");
          this.$store.commit("SET_REFRESH_TOKEN", "");
          if (confirm("비밀번호가 변경되었습니다. 다시 로그인 해주세요"))
            location.href = "http://localhost:8080/";
        }
      });
    },
  },
};
</script>

<style></style>
