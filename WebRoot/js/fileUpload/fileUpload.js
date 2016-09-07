/**
 * Created by Administrator on 2016/9/7.
 */
var fileUpload={
		timer:null,
		options:null
}

function fileUploadInit(options){
	fileUpload.options=options;
	fileUpload.timer=setInterval(fileUploadConfig,300);
}

function fileUploadConfig(){
	var options=fileUpload.options;
    var templateContent=document.getElementById(options.templateID);
    if(templateContent){
        var manualUploader = new qq.FineUploader({
            element: document.getElementById(options.templateContainer),
            template: options.templateID,
            request: {
                endpoint: options.url,
                method: options.method
            },
            thumbnails: {
                placeholders: {
                    waitingPath: 'js/fine-uploader/placeholders/waiting-generic.png',
                    notAvailablePath: 'js/fine-uploader/placeholders/not_available-generic.png'
                }
            },
            autoUpload: options.isAutoUpload,
            debug: true
        });

        qq(document.getElementById("trigger-upload")).attach("click", function () {
            manualUploader.uploadStoredFiles();
        });
        clearTimeout(timer);
    }
}