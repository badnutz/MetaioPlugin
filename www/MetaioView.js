var Metaio = function() {
};
var exec = require('cordova/exec');

// Start MetaioArelView
Metaio.prototype.startMetaio = function(successCallback, failureCallback, msg) {
	console.log(msg);
	exec(successCallback, failureCallback, 'Metaio', 'StartArelView', [msg]);
};

var metaio = new Metaio();
module.exports = metaio;