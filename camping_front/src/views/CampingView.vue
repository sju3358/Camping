<template>
  <div>
    <button
      @click="
        setCampDetailFlag(false),
          setCampMapFlag(false),
          setCampListFlag(true),
          reloadList()
      "
      v-show="listFlag == false">
      목록
    </button>
    <button
      @click="
        setCampDetailFlag(false), setCampListFlag(false), setCampMapFlag(true)
      "
      v-show="mapFlag == false">
      캠핑장 지도 검색
    </button>
    <br />
    <br />
    <CampList v-show="listFlag" @goCampDetail="getCampDetail" />
    <CampDetail v-show="detailFlag" :content_id="detailContentId" />
    <CampMap v-if="mapFlag" v-show="mapFlag" @goCampDetail="getCampDetail" />
  </div>
</template>

<script>
import CampList from "@/components/camp/CampList.vue";
import CampDetail from "@/components/camp/CampDetail.vue";
import CampMap from "@/components/camp/CampMap.vue";

export default {
  data() {
    return {
      listFlag: true,
      detailFlag: false,
      mapFlag: false,
      detailContentId: 0,
    };
  },
  components: {
    CampList,
    CampDetail,
    CampMap,
  },
  methods: {
    setCampDetailFlag(campDetailFlag) {
      this.detailFlag = campDetailFlag;
    },
    setCampMapFlag(campMapFlag) {
      this.mapFlag = campMapFlag;
    },
    setCampListFlag(campListFlag) {
      this.listFlag = campListFlag;
    },
    getCampDetail(contentId) {
      this.setCampDetailFlag(true);
      this.setCampMapFlag(false);
      this.setCampListFlag(false);
      this.detailContentId = contentId;
    },
    reloadList() {
      location.reload();
    },
  },
};
</script>
<style scoped>
input {
  width: 500px;
}
</style>
