<template>
  <div>
    <br />
    <span>정말 탈퇴 하시겠습니까?</span>
    <br />
    <button @click="deactivate">확인</button>
  </div>
</template>

<script>
import http from "@/util/http.js";
export default {
  methods: {
    deactivate() {
      http
        .delete(`/info/deactivate/${this.$store.state.userIdx}`)
        .then((res) => {
          console.log(res.data);
          if (res.data.message == "success") {
            this.$store.commit("SET_USER_NAME", "");
            this.$store.commit("SET_USER_IDX", -1);
            this.$store.commit("SET_ACCESS_TOKEN", "");
            this.$store.commit("SET_REFRESH_TOKEN", "");
            if (confirm("회원탈퇴가 완료되었습니다."))
              window.location.href = "http://localhost:8080/";
          }
        });
    },
  },
};
</script>

<style></style>
