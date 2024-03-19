//定义类型
export type FoundPram = {
  lfId:string,
  userId:string,
  lfType:string,
  lfName:string,
  lfAddress:string,
  lfImg:string,
  lfContent:string,
  lfUsername:string,
  lfPhone:string,
  isPass:string,
  ispassCause:string,
  isLost:string,
  lfTime:string,
}

//列表参数类型
export type FoundList = {
  lfName:string;
  lfType:string;
  currentPage:number;
  pageSize:number;
  total:number;
}

export type FoundStatus = {
  lfId:string,
  isPass:string,
  ispassCause:string,
}