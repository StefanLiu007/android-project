//@param requestURL
function gotoPage(url,currentPage,pageSize,searchContent){ 
//	alert("kkk");
	//alert(url+","+currentPage+","+pageSize);
//	if(true) return;
	window.location.href = url + "?searchContent="+searchContent+"&page="+currentPage+"&pagesize="+pageSize;
}