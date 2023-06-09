<template>
  <div>
    <button @click="setBoardListFlag" v-show="this.boardListFlag == false">
      글목록
    </button>
    <button
      @click="setBoardWriteFlag"
      v-show="this.boardWriteFlag == false"
      v-if="$store.state.userIdx != -1">
      글쓰기
    </button>
    <br />
    <br />
    <BoardList @goBoardDetail="getBoardDetail" v-show="boardListFlag" />
    <BoardDetail
      v-show="boardListFlag == false && boardWriteFlag == false"
      v-bind:idx="detailId" />
    <BoardWrite v-show="boardWriteFlag" />
  </div>
</template>

<script>
import BoardList from "@/components/board/BoardList.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";

export default {
  data() {
    return {
      boardListFlag: true,
      boardWriteFlag: false,
      detailId: 0,
    };
  },
  components: {
    BoardList,
    BoardDetail,
    BoardWrite,
  },
  methods: {
    setBoardListFlag() {
      this.boardListFlag = true;
      this.boardWriteFlag = false;
      location.reload();
    },
    setBoardWriteFlag() {
      this.boardWriteFlag = true;
      this.boardListFlag = false;
    },
    getBoardDetail(contentId) {
      this.boardListFlag = false;
      this.boardWriteFlag = false;
      this.detailId = contentId;
    },
  },
};
</script>
