//定义类型
export type MyPram = {
  lfId:string,
  userId:string,
  isIlk:string,
  lfType:string,
  lfName:string,
  lfAddress:string,
  lfImg:string,
  lfContent:string,
  lfUsername:string,
  lfPhone:string,
  isPass:string
  isLost:string,
  isFound:string,
  lfTime:string,
  est:string,
  mtime:string,
}

//列表参数类型
export type mylist = {
  userId:string;
  isIlk:string;
  lfName:string;
  isPass:string;
  isFound:string;
  currentPage:number;
  pageSize:number;
  total:number;
}


//定义类型
export type MyEditPram = {
  lfId:string,
  lfType:string,
  lfName:string,
  lfAddress:string,
  lfImg:string,
  lfContent:string,
  lfUsername:string,
  lfPhone:string,
  lfTime:string,
}
