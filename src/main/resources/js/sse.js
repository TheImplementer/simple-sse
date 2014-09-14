jQuery(document).ready(function () {

    function fillSection(name, data) {
        var rows = data[name].map(function (offer) {
            var row = jQuery('<div class="row"></div>');
            var price = jQuery('<div></div>').text(offer.price);
            var quantity = jQuery('<div></div>').text(offer.quantity);
            row.append(price, quantity);
            return row;
        });
        var dataContainer = jQuery('#' + name);
        dataContainer.empty();
        dataContainer.append(rows);
    }

    var eventSource = new EventSource('rest/test/sse');
    eventSource.addEventListener('message', function (event) {
        var parsedData = JSON.parse(event.data);
        fillSection('offers', parsedData);
        fillSection('demands', parsedData);
    });
});