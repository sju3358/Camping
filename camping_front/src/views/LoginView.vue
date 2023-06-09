<template>
  <div>
    <MemberLoginVue
      @register-button-click="setRegisterFlag"
      @findPassword-button-click="setFindPasswordFlag"
      v-show="registerFlag == false"
    />

    <MemberRegisterVue v-show="registerFlag == true" />

    <MemberPasswordFindVue v-show="findPasswordFlag == true" />
  </div>
</template>

<script>
import MemberLoginVue from "@/components/member/MemberLogin.vue";
import MemberRegisterVue from "@/components/member/MemberRegister.vue";
import MemberPasswordFindVue from "@/components/member/MemberPasswordFind.vue";

export default {
  data() {
    return {
      registerFlag: false,
      findPasswordFlag: false,
    };
  },
  components: {
    MemberLoginVue,
    MemberRegisterVue,
    MemberPasswordFindVue,
  },
  methods: {
    setRegisterFlag(registerFlag) {
      this.registerFlag = registerFlag;
      this.findPasswordFlag = false;
    },
    setFindPasswordFlag(findPasswordFlag) {
      this.findPasswordFlag = findPasswordFlag;
    },
  },
};
</script>

<style></style>
