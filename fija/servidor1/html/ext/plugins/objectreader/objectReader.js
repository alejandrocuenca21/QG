/*
 * Object Reader.
 * Espera algo como [{property1: 'value1', property2: 'value2'}, {...}]
 */
Ext.data.ObjectReader = function(meta, recordType){
    Ext.data.ObjectReader.superclass.constructor.call(this, meta, recordType);
};

Ext.extend(Ext.data.ObjectReader, Ext.data.DataReader, {
    readRecords: function(response){
		var sid = this.meta ? this.meta.id : null;
		var separator = this.meta ? this.meta.separator : '';
    	var recordType = this.recordType, fields = recordType.prototype.fields;
    	var records = [];
    	var root = response;
	    for(var i = 0; i < root.length; i++){
		    var obj = root[i];
	        var values = {};
	        
	        for(var j = 0, jlen = fields.length; j < jlen; j++){
                var f = fields.items[j];
                var k = f.mapping !== undefined && f.mapping !== null ? f.mapping : f.name;

                var v = obj[k] !== undefined ? obj[k] : f.defaultValue;
                v = f.convert(v);
                values[f.name] = v;
                
            }
			
			var id = null;
					
			if (Ext.isArray(sid)) {
				
				var arrId = new Array();
				
				for (var x = 0; x < sid.length; x++) {
					if (sid[x].name) {
						var value = values[sid[x].name];
						if (value !== null && value !== undefined && !Ext.isEmpty(value)) {
							arrId.push(value);
						}
					}
				}
				
				if (arrId.length > 0) {
					id = arrId.join(separator);

				}						
				
			} else {
				id = obj[sid];
			}
			
			
	        var record = new recordType(values, id);
	        records[records.length] = record;
	    }
	    return {
	        records : records,
	        totalRecords : records.length
	    };
    },
    read: function(response) {
    	this.readRecords(response);
    }
});