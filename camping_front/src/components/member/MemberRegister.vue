<template>
  <div>
    <input type="text" v-model="registerId" placeholder="아이디" /><br />
    <input
      type="password"
      v-model="registerPassword"
      placeholder="비밀번호"
    /><br />
    <input
      type="password"
      v-model="registerPasswordRepeat"
      placeholder="비밀번호 확인"
    /><br />
    <input type="text" v-model="registerName" placeholder="이름" /><br />

    <button @click="sendRegisterRequest">등록</button>
  </div>
</template>

<script>
import http from "@/util/http.js";

export default {
  data() {
    return {
      registerId: "",
      registerPassword: "",
      registerPasswordRepeat: "",
      registerName: "",
    };
  },
  methods: {
    sendRegisterRequest() {
      const data = {
        registerId: this.registerId,
        registerPassword: this.registerPassword,
        registerPasswordRepeat: this.registerPasswordRepeat,
        registerName: this.registerName,
      };
      http.post("auth/register", data).then((res) => {
        if (res.data.message == "fail") alert(res.data.error);
        else location.reload();
      });
    },
  },
};
</script>

<style></style>
