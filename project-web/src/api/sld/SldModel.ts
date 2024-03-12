//定义轮播图类型
export type Sld = {
  sldId:string,
  sldName:string,
  sldType:string,
  isEnable:string
  sldImg:string
}
//列表参数类型
export type SldListParm = {
  sldName:string;
  currentPage:number;
  pageSize:number;
  total:number;
}