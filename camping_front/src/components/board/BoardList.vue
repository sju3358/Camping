<template>
  <div>
    <table>
      <tr>
        <th>고유번호</th>
        <th style="width: 50%">제목</th>
        <th>작성 시간</th>
        <th style="width: 5%">댓글 수</th>
      </tr>
      <tr v-for="board in boardList" :key="board.idx">
        <td>{{ board.idx }}</td>
        <td>
          <div class="title" @click="moveDetail(board.idx)">
            <a>{{ board.title }}</a>
          </div>
        </td>
        <td>{{ board.write_date }}</td>
        <td>{{ board.count_review }}</td>
      </tr>
    </table>
    <button @click="movePrevPage">이전 페이지</button>
    <input
      id="page"
      class="page"
      type="number"
      @keyup.enter="searchPage"
      :value="`${now / 20 + 1}`" />
    / {{ max }}
    <button @click="moveNextPage">다음 페이지</button>
    <br />
    <input
      v-model="searchTitle"
      class="searchInput"
      placeholder="제목 검색" />&nbsp;
    <button @click="searchBoard">검색</button>
  </div>
</template>

<script>
import http from "@/util/http.js";

export default {
  data() {
    return {
      boardList: [],
      now: 0,
      max: 0,
      searchTitle: "",
      searchFlag: false,
    };
  },
  created() {
    this.getBoardList();
    this.getBoardMax();
  },
  methods: {
    getBoardList() {
      http.get(`/board/${this.now}`).then((res) => {
        console.log(res.data);
        if (res.data.message == "success") {
          this.boardList = res.data.boardList;
        } else {
          if (confirm("로그인이 필요합니다.")) {
            location.href = "http://localhost:8080/";
          }
        }
      });
    },
    getSearchBoardList() {
      if (this.searchTitle != "") {
        http
          .get(`/board/search/${this.searchTitle}/${this.now}`)
          .then((res) => {
            this.boardList = res.data;
          });
      }
    },
    searchBoard() {
      if (this.searchTitle != "") {
        this.now = 0;
        this.getSearchBoardList();
        this.searchFlag = true;
        this.getSearchMax();
      }
    },
    moveDetail(id) {
      this.$emit("goBoardDetail", id);
    },
    moveNextPage() {
      if (this.now / 20 + 1 < this.max) {
        this.now += 20;
        if (this.searchFlag) {
          this.getSearchBoardList();
        } else {
          this.getBoardList();
        }
      }
    },
    movePrevPage() {
      if (this.now != 0) {
        this.now -= 20;
        if (this.searchFlag) {
          this.getSearchBoardList();
        } else {
          this.getBoardList();
        }
      }
    },
    getBoardMax() {
      http.get("/board").then((res) => {
        this.max = res.data;
      });
    },
    getSearchMax() {
      http.get(`/board/search/${this.searchTitle}`).then((res) => {
        this.max = res.data;
      });
    },
    searchPage() {
      const page = document.getElementById("page").value;
      if (page % 1 == 0 && page > 0 && page <= this.max) {
        this.now = (page - 1) * 20;
        if (this.searchFlag) {
          this.getSearchBoardList();
        } else {
          this.getBoardList();
        }
      }
    },
  },
};
</script>

<style scoped>
table,
.title {
  margin-left: auto;
  margin-right: auto;
}
td,
th {
  width: 500px;
}
.title {
  white-space: nowrap;
  width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  cursor: pointer;
}
.page {
  width: 50px;
}
.searchInput {
  width: 300px;
}
</style>
