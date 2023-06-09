<template>
  <div>
    <div>
      <span>임시 비밀번호를 발급받을 아이디와 이메일을 입력해주세요.</span>
      <br />
      <input type="text" v-model="memberId" placeholder="아이디" /> <br />
      <input type="text" v-model="toEmail" placeholder="이메일" /><br />
      <button @click="sendTempPassword">확인</button>
    </div>
  </div>
</template>

<script>
import https from "@/util/http.js";
export default {
  data() {
    return {
      toEmail: "",
      memberId: "",
    };
  },
  methods: {
    sendTempPassword() {
      const data = {
        toEmail: this.toEmail,
        memberId: this.memberId,
      };

      https.post("/info/find-password", data).then((res) => {
        if (res.data.message == "success") {
          if (confirm("임시비밀번호가 발급되었습니다."))
            location.href = "http://localhost:8080/";
        }
      });
    },
  },
};
</script>

<style></style>
