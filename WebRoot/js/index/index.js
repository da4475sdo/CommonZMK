/**
 * Created by Administrator on 2016/9/11.
 */
var mainApp=angular.module('index_app',['ngRoute'])
	.config(['$routeProvider', function($routeProvider){
		    $routeProvider
		        .when('/',{
		        	templateUrl:'template/index/home.html',
		        	controller:'home_ctrl'
		        	})
		        .when('/projects/',{
		        	templateUrl:'template/index/projects.html',
		        	controller:'projects_ctrl'	
		        	})
                .when('/about/',{
                    templateUrl:'template/index/about.html',
                    controller:'about_ctrl'
                })
                .when('/contact/',{
                    templateUrl:'template/index/contact.html',
                    controller:'contact_ctrl'
                })
		        .otherwise({redirectTo:'/'});
	}])
	.config(function($httpProvider) {
		$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
		$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

		// Override $http service's default transformRequest
		$httpProvider.defaults.transformRequest = [function(data) {
			/**
			 * The workhorse; converts an object to x-www-form-urlencoded serialization.
			 * @param {Object} obj
			 * @return {String}
			 */
			var param = function(obj) {
				var query = '';
				var name, value, fullSubName, subName, subValue, innerObj, i;

				for (name in obj) {
					value = obj[name];

					if (value instanceof Array) {
						for (i = 0; i < value.length; ++i) {
							subValue = value[i];
							fullSubName = name + '[' + i + ']';
							innerObj = {};
							innerObj[fullSubName] = subValue;
							query += param(innerObj) + '&';
						}
					} else if (value instanceof Object) {
						for (subName in value) {
							subValue = value[subName];
							fullSubName = name + '[' + subName + ']';
							innerObj = {};
							innerObj[fullSubName] = subValue;
							query += param(innerObj) + '&';
						}
					} else if (value !== undefined && value !== null) {
						query += encodeURIComponent(name) + '='
								+ encodeURIComponent(value) + '&';
					}
				}

				return query.length ? query.substr(0, query.length - 1) : query;
			};

			return angular.isObject(data) && String(data) !== '[object File]'
					? param(data)
					: data;
			}];
		});
		