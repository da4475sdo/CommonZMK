/**
 * Created by Administrator on 2016/9/7.
 */
var timer;
$(document).ready(function() {
    timer=setInterval(fileUploadInit,1000);
});

function fileUploadInit(){
    var templateContent=document.getElementById("qq-template-manual-trigger");
    if(templateContent){
        var manualUploader = new qq.FineUploader({
            element: document.getElementById('fine-uploader-manual-trigger'),
            template: 'qq-template-manual-trigger',
            request: {
                endpoint: 'demo.do',
                method: 'POST'
            },
            thumbnails: {
                placeholders: {
                    waitingPath: 'js/fine-uploader/placeholders/waiting-generic.png',
                    notAvailablePath: 'js/fine-uploader/placeholders/not_available-generic.png'
                }
            },
            autoUpload: false,
            debug: true
        });

        qq(document.getElementById("trigger-upload")).attach("click", function () {
            manualUploader.uploadStoredFiles();
        });
        clearTimeout(timer);
    }
}