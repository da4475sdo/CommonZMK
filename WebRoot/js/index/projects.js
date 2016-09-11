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
});
