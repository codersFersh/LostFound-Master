//定义类型
export type InfoParm = {
  infoId:string,
  userId:string,
  nickName:string,
  infoPhone:string,
  infoContent:string,
  lfId:string,
  isIlk:string,
  lfUserid:string
  lfName:string,
  lfImg:string,
  lfUsername:string,
  isTrue:string;
}

//列表参数类型
export type infoListPram = {
  lfName:string;
  lfUserid:string;
  isIlk:string;
  currentPage:number;
  pageSize:number;
  total:number;
}


//列表参数类型
export type myInfoParm = {
  lfName:string;
  UserId:string;
  isIlk:string;
  currentPage:number;
  pageSize:number;
  total:number;
}
//列表参数类型
export type AdminInfoParm = {
  lfName:string;
  isIlk:string;
  currentPage:number;
  pageSize:number;
  total:number;
}



//定义类型
export type EditParm = {
  isTrue:string;
}

export type EditMyInfoPram = {
  infoPhone:string,
  infoContent:string,
}
