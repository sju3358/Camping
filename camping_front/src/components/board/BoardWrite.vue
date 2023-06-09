<template>
  <div>
    제목 <input class="inputTitle" v-model="board.title" /> <br />
    <br /><textarea class="text" v-model="board.text" />
    <br />
    <button @click="registBoard">등록</button>
  </div>
</template>

<script>
import http from "@/util/http.js";

export default {
  data() {
    return {
      board: {
        user_idx: "",
        title: "",
        text: "",
      },
    };
  },
  created() {
    this.board.user_idx = this.$store.state.userIdx;
  },
  methods: {
    registBoard() {
      http.post("/board", this.board).then((res) => {
        console.log(res.data);
        location.reload();
      });
    },
  },
};
</script>

<style scoped>
input {
  width: 800px;
}
.text {
  width: 1000px;
  height: 400px;
  resize: none;
}
</style>
