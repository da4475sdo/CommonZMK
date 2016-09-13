/**
 * Created by Administrator on 2016/9/11.
 */
mainApp.controller('projects_ctrl', function($scope,$route,$http) {
	var json={
            "properties":["projectID","pic","title","description"],
            "pagination":1,
            "sortField":["projectID"],
            "order":["desc"],
            "pagination":0,
            "limit":10,
            "needLink":false
	};
	var req = {
			 method: 'POST',
			 url: 'getProjects.do',
			 data: { json: JSON.stringify(json) }
			}

	$http(req).then(function(response) {
				$scope.projects = response.data;
			});
	$scope.limitChar=function (str){
		if(str.length>89){
			str=str.substring(0,89)+"...";
		}
		return str;
	}
    //轮播控制
    // 循环轮播到上一个项目
    $(".prev-slide").click(function(){
        $("#project-carousel").carousel('prev');
    });
    // 循环轮播到下一个项目
    $(".next-slide").click(function(){
        $("#project-carousel").carousel('next');
    });
});
