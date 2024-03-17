//定义轮播图类型
export type IlkPram = {
  ilkId:string,
  ilkName:string,
}
//列表参数类型
export type IlkList = {
  ilkName:string;
  currentPage:number;
  pageSize:number;
  total:number;
}