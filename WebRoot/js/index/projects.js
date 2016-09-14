/**
 * Created by Administrator on 2016/9/11.
 */
mainApp.controller('projects_ctrl', function($scope,$route,$http) {
	var json={
            "properties":["projectID","pic","title","description"],
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
	
	$scope.getProjectDetails=function (obj){
		var projectID=obj.project.projectID;
		var projectDetailJson={
	            "properties":["projectDetailID","projectPic","projectDescription"],
	            "condition":[{
	            	name:'projectID',
	            	value:projectID,
	            	operation:'=',
	            	isCombination:'false',
	            	type:0,
	            	paramName:'projectID'
	            }],
	            "sortField":["projectDetailID"],
	            "order":["asc"],
	            "pagination":0,
	            "limit":20,
	            "needLink":false
		};
		
		$http({
			 method: 'POST',
			 url: 'getProjectDetails.do',
			 data: { conditionJson: JSON.stringify(projectDetailJson) }
			}).then(function(response) {
				var dataList=response.data;
				for(var i=0,len=dataList.length;i<len;i++){
					if(i!==0){
						dataList[i].active="";
					}else{
						dataList[i].active="active";
					}
				}
				$scope.projectDetails = dataList;
		});
	}
	
    $(".prev-slide").click(function(){
        $("#project-carousel").carousel('prev');
    });

    $(".next-slide").click(function(){
        $("#project-carousel").carousel('next');
    });
    
    $('#project-detail-modal').on('hide.bs.modal', function () {
    	$('#project-carousel').carousel('pause');
    	$('#project-carousel').carousel(0);
    })
});
