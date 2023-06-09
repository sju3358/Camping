<template>
  <div>
    <table>
      <tr>
        <th>캠핑장 번호</th>
        <th>캠핑장명</th>
        <th>위치</th>
        <th>즐겨찾기 수</th>
      </tr>
      <tr v-for="camp in campList" :key="camp.content_id">
        <td>{{ camp.content_id }}</td>
        <td>
          <div class="title" @click="moveDetail(camp.content_id)">
            {{ camp.title }}
          </div>
        </td>
        <td>
          <div class="addr">{{ camp.addr }}</div>
        </td>
        <td>{{ camp.count_favorites }}</td>
      </tr>
    </table>
    <button @click="movePrevPage">이전 페이지</button>
    <input
      id="page"
      type="number"
      @keyup.enter="searchPage"
      :value="`${now / 20 + 1}`"
    />
    / {{ max }}
    <button @click="moveNextPage">다음 페이지</button>
  </div>
</template>

<script>
import http from "@/util/http.js";

export default {
  data() {
    return {
      campList: [],
      now: 0,
      max: 0,
    };
  },
  created() {
    this.getCampList();
    this.getCampMax();
  },
  methods: {
    getCampList() {
      http.get(`/camp/${this.now}`).then((res) => {
        this.campList = res.data;
      });
    },
    moveDetail(contentId) {
      this.$emit("goCampDetail", contentId);
    },
    moveNextPage() {
      if (this.now / 20 + 1 < this.max) {
        this.now += 20;
        this.getCampList();
      }
    },
    movePrevPage() {
      if (this.now != 0) {
        this.now -= 20;
        this.getCampList();
      }
    },
    getCampMax() {
      http.get("/camp").then((res) => {
        this.max = res.data;
      });
    },
    searchPage() {
      const page = document.getElementById("page").value;
      if (page % 1 == 0 && page > 0 && page <= this.max) {
        this.now = (page - 1) * 20;
        this.getCampList();
      }
    },
  },
};
</script>

<style scoped>
table,
.title,
.addr {
  margin-left: auto;
  margin-right: auto;
}
td,
th {
  width: 500px;
}
.title,
.addr {
  white-space: nowrap;
  width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.title {
  cursor: pointer;
}
input {
  width: 50px;
}
</style>
