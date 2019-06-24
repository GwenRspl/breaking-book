(window.webpackJsonp=window.webpackJsonp||[]).push([[132],{vIqn:function(t,e,n){"use strict";n.r(e),n.d(e,"IonRange",function(){return s});var i=n("B5Ai"),a=n("cBjU"),r=n("d6Vy"),o=n("JvIM"),s=function(){function t(){var t=this;this.noUpdate=!1,this.hasFocus=!1,this.ratioA=0,this.ratioB=0,this.debounce=0,this.name="",this.dualKnobs=!1,this.min=0,this.max=100,this.pin=!1,this.snaps=!1,this.step=1,this.ticks=!0,this.disabled=!1,this.value=0,this.clampBounds=function(e){return Object(o.h)(t.min,e,t.max)},this.ensureValueInBounds=function(e){return t.dualKnobs?{lower:t.clampBounds(e.lower),upper:t.clampBounds(e.upper)}:t.clampBounds(e)},this.handleKeyboard=function(e,n){var i=t.step;i=i>0?i:1,i/=t.max-t.min,n||(i*=-1),"A"===e?t.ratioA=Object(o.h)(0,t.ratioA+i,1):t.ratioB=Object(o.h)(0,t.ratioB+i,1),t.updateValue()}}return t.prototype.debounceChanged=function(){this.ionChange=Object(o.f)(this.ionChange,this.debounce)},t.prototype.minChanged=function(){this.noUpdate||this.updateRatio()},t.prototype.maxChanged=function(){this.noUpdate||this.updateRatio()},t.prototype.disabledChanged=function(){this.gesture&&this.gesture.setDisabled(this.disabled),this.emitStyle()},t.prototype.valueChanged=function(t){this.noUpdate||this.updateRatio(),t=this.ensureValueInBounds(t),this.ionChange.emit({value:t})},t.prototype.onBlur=function(){this.hasFocus&&(this.hasFocus=!1,this.ionBlur.emit(),this.emitStyle())},t.prototype.onFocus=function(){this.hasFocus||(this.hasFocus=!0,this.ionFocus.emit(),this.emitStyle())},t.prototype.componentWillLoad=function(){this.updateRatio(),this.debounceChanged(),this.emitStyle()},t.prototype.componentDidLoad=function(){return i.a(this,void 0,void 0,function(){var t,e=this;return i.c(this,function(i){switch(i.label){case 0:return t=this,[4,n.e(0).then(n.bind(null,"k6lV"))];case 1:return t.gesture=i.sent().createGesture({el:this.rangeSlider,queue:this.queue,gestureName:"range",gesturePriority:100,threshold:0,onStart:function(t){return e.onStart(t)},onMove:function(t){return e.onMove(t)},onEnd:function(t){return e.onEnd(t)}}),this.gesture.setDisabled(this.disabled),[2]}})})},t.prototype.componentDidUnload=function(){this.gesture&&(this.gesture.destroy(),this.gesture=void 0)},t.prototype.getValue=function(){var t=this.value||0;return this.dualKnobs?"object"==typeof t?t:{lower:0,upper:t}:"object"==typeof t?t.upper:t},t.prototype.emitStyle=function(){this.ionStyle.emit({interactive:!0,"interactive-disabled":this.disabled})},t.prototype.onStart=function(t){var e=this.rect=this.rangeSlider.getBoundingClientRect(),n=t.currentX,i=Object(o.h)(0,(n-e.left)/e.width,1);"rtl"===this.doc.dir&&(i=1-i),this.pressedKnob=!this.dualKnobs||Math.abs(this.ratioA-i)<Math.abs(this.ratioB-i)?"A":"B",this.setFocus(this.pressedKnob),this.update(n)},t.prototype.onMove=function(t){this.update(t.currentX)},t.prototype.onEnd=function(t){this.update(t.currentX),this.pressedKnob=void 0},t.prototype.update=function(t){var e=this.rect,n=Object(o.h)(0,(t-e.left)/e.width,1);"rtl"===this.doc.dir&&(n=1-n),this.snaps&&(n=b(d(n,this.min,this.max,this.step),this.min,this.max)),"A"===this.pressedKnob?this.ratioA=n:this.ratioB=n,this.updateValue()},Object.defineProperty(t.prototype,"valA",{get:function(){return d(this.ratioA,this.min,this.max,this.step)},enumerable:!0,configurable:!0}),Object.defineProperty(t.prototype,"valB",{get:function(){return d(this.ratioB,this.min,this.max,this.step)},enumerable:!0,configurable:!0}),Object.defineProperty(t.prototype,"ratioLower",{get:function(){return this.dualKnobs?Math.min(this.ratioA,this.ratioB):0},enumerable:!0,configurable:!0}),Object.defineProperty(t.prototype,"ratioUpper",{get:function(){return this.dualKnobs?Math.max(this.ratioA,this.ratioB):this.ratioA},enumerable:!0,configurable:!0}),t.prototype.updateRatio=function(){var t=this.getValue(),e=this.min,n=this.max;this.dualKnobs?(this.ratioA=b(t.lower,e,n),this.ratioB=b(t.upper,e,n)):this.ratioA=b(t,e,n)},t.prototype.updateValue=function(){this.noUpdate=!0;var t=this.valA,e=this.valB;this.value=this.dualKnobs?{lower:Math.min(t,e),upper:Math.max(t,e)}:t,this.noUpdate=!1},t.prototype.setFocus=function(t){if(this.el.shadowRoot){var e=this.el.shadowRoot.querySelector("A"===t?".range-knob-a":".range-knob-b");e&&e.focus()}},t.prototype.hostData=function(){var t;return{class:Object.assign({},Object(r.c)(this.color),(t={},t[""+this.mode]=!0,t["in-item"]=Object(r.d)("ion-item",this.el),t["range-disabled"]=this.disabled,t["range-pressed"]=void 0!==this.pressedKnob,t["range-has-pin"]=this.pin,t))}},t.prototype.render=function(){var t=this,e=this,n=e.min,i=e.max,r=e.step,o=e.ratioLower,s=e.ratioUpper,d=100*o+"%",p=100-100*s+"%",u="rtl"===this.doc.dir,h=u?"right":"left",c=u?"left":"right",g=[];if(this.snaps&&this.ticks)for(var m=n;m<=i;m+=r){var f=b(m,n,i),v={ratio:f,active:f>=o&&f<=s};v[h]=100*f+"%",g.push(v)}var k,x=function(t){var e={};return e[h]=t[h],e};return[Object(a.b)("slot",{name:"start"}),Object(a.b)("div",{class:"range-slider",ref:function(e){return t.rangeSlider=e}},g.map(function(t){return Object(a.b)("div",{style:x(t),role:"presentation",class:{"range-tick":!0,"range-tick-active":t.active}})}),Object(a.b)("div",{class:"range-bar",role:"presentation"}),Object(a.b)("div",{class:"range-bar range-bar-active",role:"presentation",style:(k={},k[h]=d,k[c]=p,k)}),l(u,{knob:"A",pressed:"A"===this.pressedKnob,value:this.valA,ratio:this.ratioA,pin:this.pin,disabled:this.disabled,handleKeyboard:this.handleKeyboard,min:n,max:i}),this.dualKnobs&&l(u,{knob:"B",pressed:"B"===this.pressedKnob,value:this.valB,ratio:this.ratioB,pin:this.pin,disabled:this.disabled,handleKeyboard:this.handleKeyboard,min:n,max:i})),Object(a.b)("slot",{name:"end"})]},Object.defineProperty(t,"is",{get:function(){return"ion-range"},enumerable:!0,configurable:!0}),Object.defineProperty(t,"encapsulation",{get:function(){return"shadow"},enumerable:!0,configurable:!0}),Object.defineProperty(t,"properties",{get:function(){return{color:{type:String,attr:"color"},debounce:{type:Number,attr:"debounce",watchCallbacks:["debounceChanged"]},disabled:{type:Boolean,attr:"disabled",watchCallbacks:["disabledChanged"]},doc:{context:"document"},dualKnobs:{type:Boolean,attr:"dual-knobs"},el:{elementRef:!0},max:{type:Number,attr:"max",watchCallbacks:["maxChanged"]},min:{type:Number,attr:"min",watchCallbacks:["minChanged"]},mode:{type:String,attr:"mode"},name:{type:String,attr:"name"},pin:{type:Boolean,attr:"pin"},pressedKnob:{state:!0},queue:{context:"queue"},ratioA:{state:!0},ratioB:{state:!0},snaps:{type:Boolean,attr:"snaps"},step:{type:Number,attr:"step"},ticks:{type:Boolean,attr:"ticks"},value:{type:Number,attr:"value",mutable:!0,watchCallbacks:["valueChanged"]}}},enumerable:!0,configurable:!0}),Object.defineProperty(t,"events",{get:function(){return[{name:"ionChange",method:"ionChange",bubbles:!0,cancelable:!0,composed:!0},{name:"ionStyle",method:"ionStyle",bubbles:!0,cancelable:!0,composed:!0},{name:"ionFocus",method:"ionFocus",bubbles:!0,cancelable:!0,composed:!0},{name:"ionBlur",method:"ionBlur",bubbles:!0,cancelable:!0,composed:!0}]},enumerable:!0,configurable:!0}),Object.defineProperty(t,"listeners",{get:function(){return[{name:"focusout",method:"onBlur"},{name:"focusin",method:"onFocus"}]},enumerable:!0,configurable:!0}),Object.defineProperty(t,"style",{get:function(){return":host{--knob-handle-size:calc(var(--knob-size) * 2);display:-ms-flexbox;display:flex;position:relative;-ms-flex:3;flex:3;-ms-flex-align:center;align-items:center;font-family:var(--ion-font-family,inherit);-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;z-index:2}:host(.range-disabled){pointer-events:none}::slotted(ion-label){-ms-flex:initial;flex:initial}::slotted(ion-icon[slot]){font-size:24px}.range-slider{position:relative;-ms-flex:1;flex:1;width:100%;height:var(--height);contain:size layout style;cursor:-webkit-grab;cursor:grab;-ms-touch-action:pan-y;touch-action:pan-y}:host(.range-pressed) .range-slider{cursor:-webkit-grabbing;cursor:grabbing}.range-pin{position:absolute;background:var(--ion-color-base);color:var(--ion-color-contrast);-webkit-box-sizing:border-box;box-sizing:border-box}.range-knob-handle{left:0;top:calc((var(--height) - var(--knob-handle-size)) / 2);margin-left:calc(0px - var(--knob-handle-size) / 2);position:absolute;width:var(--knob-handle-size);height:var(--knob-handle-size);text-align:center}:host-context([dir=rtl]) .range-knob-handle{right:0}@supports ((-webkit-margin-start:0) or (margin-inline-start:0)) or (-webkit-margin-start:0){.range-knob-handle{margin-left:unset;-webkit-margin-start:calc(0px - var(--knob-handle-size) / 2);margin-inline-start:calc(0px - var(--knob-handle-size) / 2)}}:host-context([dir=rtl]) .range-knob-handle{left:unset}.range-knob-handle:active,.range-knob-handle:focus{outline:none}.range-bar{border-radius:var(--bar-border-radius);left:0;top:calc((var(--height) - var(--bar-height)) / 2);position:absolute;width:100%;height:var(--bar-height);background:var(--bar-background);pointer-events:none}:host-context([dir=rtl]) .range-bar{right:0;left:unset}.range-knob{border-radius:var(--knob-border-radius);left:calc(50% - var(--knob-size) / 2);top:calc(50% - var(--knob-size) / 2);position:absolute;width:var(--knob-size);height:var(--knob-size);background:var(--knob-background);-webkit-box-shadow:var(--knob-box-shadow);box-shadow:var(--knob-box-shadow);z-index:2;pointer-events:none}:host-context([dir=rtl]) .range-knob{right:calc(50% - var(--knob-size) / 2);left:unset}:host(.range-pressed) .range-bar-active{will-change:left,right}:host(.in-item){width:100%}:host(.in-item) ::slotted(ion-label){-ms-flex-item-align:center;align-self:center}:host{--knob-border-radius:50%;--knob-background:var(--ion-background-color,#fff);--knob-box-shadow:0 3px 1px rgba(0,0,0,0.1),0 4px 8px rgba(0,0,0,0.13),0 0 0 1px rgba(0,0,0,0.02);--knob-size:28px;--bar-height:1px;--bar-background:var(--ion-color-step-250,#bfbfbf);--bar-background-active:var(--ion-color-primary,#3880ff);--bar-border-radius:0;--height:42px;padding-left:16px;padding-right:16px;padding-top:8px;padding-bottom:8px}@supports ((-webkit-margin-start:0) or (margin-inline-start:0)) or (-webkit-margin-start:0){:host{padding-left:unset;padding-right:unset;-webkit-padding-start:16px;padding-inline-start:16px;-webkit-padding-end:16px;padding-inline-end:16px}}:host(.ion-color) .range-bar-active,:host(.ion-color) .range-tick-active{background:var(--ion-color-base)}::slotted([slot=start]){margin-left:0;margin-right:16px;margin-top:0;margin-bottom:0}@supports ((-webkit-margin-start:0) or (margin-inline-start:0)) or (-webkit-margin-start:0){::slotted([slot=start]){margin-left:unset;margin-right:unset;-webkit-margin-start:0;margin-inline-start:0;-webkit-margin-end:16px;margin-inline-end:16px}}::slotted([slot=end]){margin-left:16px;margin-right:0;margin-top:0;margin-bottom:0}@supports ((-webkit-margin-start:0) or (margin-inline-start:0)) or (-webkit-margin-start:0){::slotted([slot=end]){margin-left:unset;margin-right:unset;-webkit-margin-start:16px;margin-inline-start:16px;-webkit-margin-end:0;margin-inline-end:0}}:host(.range-has-pin){padding-top:20px}.range-bar-active{bottom:0;width:auto;background:var(--bar-background-active)}.range-tick{margin-left:-.5px;border-radius:0;position:absolute;top:17.5px;width:1px;height:8px;background:var(--ion-color-step-250,#bfbfbf);pointer-events:none}@supports ((-webkit-margin-start:0) or (margin-inline-start:0)) or (-webkit-margin-start:0){.range-tick{margin-left:unset;-webkit-margin-start:-.5px;margin-inline-start:-.5px}}.range-tick-active{background:var(--bar-background-active)}.range-pin{-webkit-transform:translate3d(0,28px,0) scale(.01);transform:translate3d(0,28px,0) scale(.01);padding-left:8px;padding-right:8px;padding-top:8px;padding-bottom:8px;display:inline-block;position:relative;top:-20px;min-width:28px;-webkit-transition:-webkit-transform .12s ease;transition:-webkit-transform .12s ease;transition:transform .12s ease;transition:transform .12s ease,-webkit-transform .12s ease;background:transparent;color:var(--ion-text-color,#000);font-size:12px;text-align:center}@supports ((-webkit-margin-start:0) or (margin-inline-start:0)) or (-webkit-margin-start:0){.range-pin{padding-left:unset;padding-right:unset;-webkit-padding-start:8px;padding-inline-start:8px;-webkit-padding-end:8px;padding-inline-end:8px}}.range-knob-pressed .range-pin{-webkit-transform:translateZ(0) scale(1);transform:translateZ(0) scale(1)}:host(.range-disabled){opacity:.5}"},enumerable:!0,configurable:!0}),Object.defineProperty(t,"styleMode",{get:function(){return"ios"},enumerable:!0,configurable:!0}),t}();function l(t,e){var n,i=e.knob,r=e.value,o=e.min,s=e.max,l=e.disabled,d=e.pin,b=e.handleKeyboard;return Object(a.b)("div",{onKeyDown:function(t){var e=t.key;"ArrowLeft"===e||"ArrowDown"===e?(b(i,!1),t.preventDefault(),t.stopPropagation()):"ArrowRight"!==e&&"ArrowUp"!==e||(b(i,!0),t.preventDefault(),t.stopPropagation())},class:{"range-knob-handle":!0,"range-knob-a":"A"===i,"range-knob-b":"B"===i,"range-knob-pressed":e.pressed,"range-knob-min":r===o,"range-knob-max":r===s},style:(n={},n[t?"right":"left"]=100*e.ratio+"%",n),role:"slider",tabindex:l?-1:0,"aria-valuemin":o,"aria-valuemax":s,"aria-disabled":l?"true":null,"aria-valuenow":r},d&&Object(a.b)("div",{class:"range-pin",role:"presentation"},Math.round(r)),Object(a.b)("div",{class:"range-knob",role:"presentation"}))}function d(t,e,n,i){var a=(n-e)*t;return i>0&&(a=Math.round(a/i)*i+e),Object(o.h)(e,a,n)}function b(t,e,n){return Object(o.h)(0,(t-e)/(n-e),1)}}}]);