//定义类型
export type LostPram = {
  lfId:string,
  userId:string,
  lfType:string,
  lfName:string,
  lfAddress:string,
  lfImg:string,
  lfContent:string,
  lfUsername:string,
  lfPhone:string,
  isLost:string,
  lfTime:string,
}

//列表参数类型
export type LostList = {
  lfName:string;
  lfType:string;
  currentPage:number;
  pageSize:number;
  total:number;
}
export type LostStatus = {
  lfId:string,
  isPass:string,
  ispassCause:string,
}
