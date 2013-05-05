setInputTitles = ->
  inputs = $('input[type=text], input[type=password]')
  inputs.each ->
    input = $(@)

    input.tipsy({gravity: 's', trigger: 'focus', opacity: 0.9});
    input.tipsy({gravity: 's', trigger: 'hover', opacity: 0.9});

    if (input.val() == '')
      input.val(input.attr('original-title'))

    inputs.focus ->
      input = $(@)

      if (input.val() == input.attr('original-title'))
        input.val('')

    inputs.blur ->
      input = $(@)

      if (input.val() == '')
        input.val(input.attr('original-title'))

$ ->
  $('#content').masonry({
    itemSelector: '.item',
    isFitWidth: true,
    columnWidth: 325
  })

  setInputTitles()