<template>
  <div>
    <table>
      <tr>
        <th>제목</th>
      </tr>
      <tr>
        <td>{{ board.title }}</td>
      </tr>
    </table>
    ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    <br />
    <div class="content">
      <h1>내용</h1>
      <br />
      {{ board.text }}
    </div>
    <br />
    <br />
    <button
      v-show="board.user_idx == $store.state.userIdx"
      @click="deleteBoard()">
      글 삭제
    </button>
    <br />
    ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    <br />
    <h2>댓글</h2>
    <br />
    <textarea class="comment" id="comment" v-model="review.comment" />
    <button class="registBtn" @click="registReview">등록</button>
    <br />
    <br />
    <table>
      <tr>
        <th>내용</th>
        <th>작성 시간</th>
      </tr>
      <tr v-for="review in reviewList" :key="review.idx">
        <td>{{ review.comment }}</td>
        <td>{{ review.write_date }}</td>
        <button
          v-show="review.user_idx == $store.state.userIdx"
          @click="deleteReview(review.idx)">
          삭제
        </button>
      </tr>
    </table>
    <button @click="movePrevPage">이전 페이지</button>
    {{ now / 5 + 1 }} / {{ Math.ceil(board.count_review / 5) }}
    <button @click="moveNextPage">다음 페이지</button>
  </div>
</template>

<script>
import http from "@/util/http.js";

export default {
  data() {
    return {
      board: {
        idx: 0,
        author: "",
        title: "",
        text: "",
        count_review: 0,
      },
      reviewList: [],
      review: {
        content_id: 0,
        user_idx: "",
        comment: "",
      },
      now: 0,
    };
  },
  created() {},
  props: ["idx"],
  watch: {
    idx() {
      http.get(`/board/content/${this.idx}`).then((res) => {
        this.board = res.data;
        this.getReviewList();
        this.review.content_id = this.board.idx;
        this.review.user_idx = this.$store.state.userIdx;
      });
      this.now = 0;
    },
  },
  methods: {
    getReviewList() {
      http.get(`/review/${this.board.idx}/${this.now}`).then((res) => {
        this.reviewList = res.data;
      });
    },
    moveNextPage() {
      if (this.now / 5 + 1 < Math.ceil(this.board.count_review / 5)) {
        this.now += 5;
        this.getReviewList();
      }
    },
    movePrevPage() {
      if (this.now != 0) {
        this.now -= 5;
        this.getReviewList();
      }
    },
    registReview() {
      http.post("/review", this.review).then(() => {
        this.now = 0;
        http.get(`/board/content/${this.board.idx}`).then((res) => {
          this.board = res.data;
          this.getReviewList();
          this.review.comment = "";
        });
      });
    },
    deleteReview(reviewIdx) {
      http.delete(`/review/${reviewIdx}`).then(() => {
        this.now = 0;
        http.get(`/board/content/${this.board.idx}`).then((res) => {
          this.board = res.data;
          this.getReviewList();
        });
      });
    },
    deleteBoard() {
      if (this.board.count_review > 0) {
        this.deleteAllReview(this.board.idx);
        http.delete(`/board/${this.board.idx}`);
      } else {
        http.delete(`/board/${this.board.idx}`);
      }
      location.reload();
    },
    deleteAllReview(idx) {
      http.delete(`/review/content/${idx}`);
    },
  },
};
</script>

<style scoped>
table,
.content {
  margin-left: auto;
  margin-right: auto;
}

td,
th {
  width: 500px;
}
.content {
  width: 700px;
}

.comment {
  width: 700px;
  height: 50px;
  resize: none;
}

.comment,
.registBtn {
  vertical-align: middle;
}

.registBtn {
  height: 50px;
}
</style>
