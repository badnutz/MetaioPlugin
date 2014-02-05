    var Metaio = function () {};

    //Start MetaioArelView
    Metaio.prototype.startMetaio = function (msg, duration, successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'MetaioPlugin', null, [msg]);
    };

    var metaio = new Metaio();
    module.exports = metaio;

