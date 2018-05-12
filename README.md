#  OpenGL ES2.0

<h1 id="着色器语言简介">着色器语言简介</h1>

<p>OpenGLES的着色器语言GLSL是一种高级的图形化编程语言，其源自应用广泛的C语言。与传统的C语言不同的是，它提供了更加丰富的针对于图像处理的原生类型，诸如向量、矩阵之类。OpenGLES 主要包含以下特性：</p>

<ul>
<li>GLSL是一种面向过程的语言，和Java的面向对象是不同的。</li>
<li>GLSL的基本语法与C/C++基本相同。</li>
<li>它完美的支持向量和矩阵操作。</li>
<li>它是通过限定符操作来管理输入输出类型的。</li>
<li>GLSL提供了大量的内置函数来提供丰富的扩展功能。</li>
</ul>

<p>在前面的博客示例中，我们所使用的都是非常简单的着色器，基本没有使用过GLSL的内置函数，在后面使用光照、贴图等等其他功能，我们不可避免的要使用这些内置函数。</p>



<h1 id="着色器语言基础">着色器语言基础</h1>

<p>GLSL虽然是基于C/C++的语言，但是它和C/C++还是有很大的不同的，比如在GLSL中没有<code>double</code>、<code>long</code>等类型，没有<code>union</code>、<code>enum</code>、<code>unsigned</code>以及位运算等特性。</p>



<h2 id="数据类型">数据类型</h2>

<p>GLSL中的数据类型主要分为标量、向量、矩阵、采样器、结构体、数组、空类型七种类型：</p>

<ul>
<li>标量：标量表示的是只有大小没有方向的量，在GLSL中标量只有<strong>bool、int和float</strong>三种。对于int，和C一样，可以写为十进制（16）、八进制（020）或者十六进制（0x10）。关于进制不了解的，得自己补补，这是编程基础。<strong>对于标量的运算，我们最需要注意的是精度，防止溢出问题</strong>。</li>
<li><p>向量：向量我们可以看做是数组，在GLSL通常用于储存颜色、坐标等数据，针对维数，可分为二维、三维和四位向量。针对存储的标量类型，可以分为bool、int和float。共有vec2、vec3、vec4，ivec2、ivec3、ivec4、bvec2、bvec3和bvec4九种类型，数组代表维数、i表示int类型、b表示bool类型。<strong>需要注意的是，GLSL中的向量表示竖向量，所以与矩阵相乘进行变换时，矩阵在前，向量在后（与DirectX正好相反）。</strong>向量在GPU中由硬件支持运算，比CPU快的多。</p>

<ol><li>作为颜色向量时，用rgba表示分量，就如同取数组的中具体数据的索引值。三维颜色向量就用rgb表示分量。比如对于颜色向量vec4 color，color[0]和color.r都表示color向量的第一个值，也就是红色的分量。其他相同。</li>
<li>作为位置向量时，用xyzw表示分量，xyz分别表示xyz坐标，w表示向量的模。三维坐标向量为xyz表示分量，二维向量为xy表示分量。</li>
<li>作为纹理向量时，用stpq表示分量，三维用stp表示分量，二维用st表示分量。</li></ol></li>
<li><p>矩阵：在GLSL中矩阵拥有2*2、3*3、4*4三种类型的矩阵，分别用mat2、mat3、mat4表示。我们可以把矩阵看做是一个二维数组，也可以用二维数组下表的方式取里面具体位置的值。</p></li>
<li>采样器：采样器是专门用来对纹理进行采样工作的，在GLSL中一般来说，一个采样器变量表示一副或者一套纹理贴图。所谓的纹理贴图可以理解为我们看到的物体上的皮肤。</li>
<li>结构体：和C语言中的结构体相同，用struct来定义结构体，关于结构体参考C语言中的结构体。</li>
<li>数组：数组知识也和C中相同，不同的是数组声明时可以不指定大小，但是建议在不必要的情况下，还是指定大小的好。</li>
<li>空类型：空类型用void表示，仅用来声明不返回任何值得函数。</li>
</ul>

<p>变量声明示例：</p>



<pre class="prettyprint"><code class="language-C hljs glsl"><span class="hljs-keyword">float</span> a=<span class="hljs-number">1.0</span>;
<span class="hljs-keyword">int</span> b=<span class="hljs-number">1</span>;
<span class="hljs-keyword">bool</span> c=<span class="hljs-literal">true</span>;
<span class="hljs-keyword">vec2</span> d=<span class="hljs-keyword">vec2</span>(<span class="hljs-number">1.0</span>,<span class="hljs-number">2.0</span>);
<span class="hljs-keyword">vec3</span> e=<span class="hljs-keyword">vec3</span>(<span class="hljs-number">1.0</span>,<span class="hljs-number">2.0</span>,<span class="hljs-number">3.0</span>)
<span class="hljs-keyword">vec4</span> f=<span class="hljs-keyword">vec4</span>(<span class="hljs-keyword">vec3</span>,<span class="hljs-number">1.2</span>);
<span class="hljs-keyword">vec4</span> g=<span class="hljs-keyword">vec4</span>(<span class="hljs-number">0.2</span>);  <span class="hljs-comment">//相当于vec(0.2,0.2,0.2,0.2)</span>
<span class="hljs-keyword">vec4</span> h=<span class="hljs-keyword">vec4</span>(a,a,<span class="hljs-number">1.3</span>,a);
<span class="hljs-keyword">mat2</span> i=<span class="hljs-keyword">mat2</span>(<span class="hljs-number">0.1</span>,<span class="hljs-number">0.5</span>,<span class="hljs-number">1.2</span>,<span class="hljs-number">2.4</span>);
<span class="hljs-keyword">mat2</span> j=<span class="hljs-keyword">mat2</span>(<span class="hljs-number">0.8</span>);   <span class="hljs-comment">//相当于mat2(0.8,0.8,0.8,0.8)</span>
<span class="hljs-keyword">mat3</span> k=<span class="hljs-keyword">mat3</span>(e,e,<span class="hljs-number">1.2</span>,<span class="hljs-number">1.6</span>,<span class="hljs-number">1.8</span>);</code></pre>



<h2 id="运算符">运算符</h2>

<p>GLSL中的运算符有（越靠前，运算优先级越高）： <br>
1. 索引：[] <br>
2. 前缀自加和自减：++，– <br>
3. 一元非和逻辑非：~，！ <br>
4.  加法和减法：+，- <br>
5. 等于和不等于：==，！= <br>
6. 逻辑异或：^^ <br>
7. 三元运算符号，选择：？: <br>
8. 成员选择与混合：. <br>
9. 后缀自加和自减：++，– <br>
10. 乘法和除法：*，/ <br>
11. 关系运算符：&gt;，&lt;，=，&gt;=，&lt;=，&lt;&gt; <br>
12. 逻辑与：&amp;&amp; <br>
13. 逻辑或：|| <br>
14. 赋值预算：=，+=，-=，*=，/=</p>



<h2 id="类型转换">类型转换</h2>

<p>GLSL的类型转换与C不同。在GLSL中类型不可以自动提升，比如<code>float a=1;</code>就是一种错误的写法，必须严格的写成<code>float a=1.0</code>，也不可以强制转换，即<code>float a=(float)1;</code>也是错误的写法，但是可以用内置函数来进行转换，如<code>float a=float(1);</code>还有<code>float a=float(true);</code>（true为1.0，false为0.0）等，值得注意的是，<strong>低精度的int不能转换为低精度的float</strong>。。</p>



<h2 id="限定符">限定符</h2>

<p>在之前的博客中也提到了，GLSL中的限定符号主要有：</p>

<ul>
<li>attritude：一般用于各个顶点各不相同的量。如顶点颜色、坐标等。</li>
<li>uniform：一般用于对于3D物体中所有顶点都相同的量。比如光源位置，统一变换矩阵等。</li>
<li>varying：表示易变量，一般用于顶点着色器传递到片元着色器的量。</li>
<li>const：常量。</li>
</ul>

<p>限定符与java限定符类似，放在变量类型之前，并且只能用于全局变量。在GLSL中，没有默认限定符一说。</p>



<h2 id="流程控制">流程控制</h2>

<p>GLSL中的流程控制与C中基本相同，主要有：</p>

<ul>
<li>if(){}、if(){}else{}、if(){}else if(){}else{}</li>
<li>while(){}和do{}while()</li>
<li>for(;;){}</li>
<li>break和continue</li>
</ul>



<h2 id="函数">函数</h2>

<p>GLSL中也可以定义函数，定义函数的方式也与C语言基本相同。函数的返回值可以是GLSL中的除了采样器的任意类型。对于GLSL中函数的参数，可以用参数用途修饰符来进行修饰，常用修饰符如下：</p>

<ul>
<li>in：输入参数，无修饰符时默认为此修饰符。</li>
<li>out：输出参数。</li>
<li>inout：既可以作为输入参数，又可以作为输出参数。</li>
</ul>

<h1 id="内建变量">内建变量</h1>

<p>在着色器中我们一般都会声明变量来在程序中使用，但是着色器中还有一些特殊的变量，不声明也可以使用。这些变量叫做内建变量。<strong>內建变量，相当于着色器硬件的输入和输出点，使用者利用这些输入点输入之后，就会看到屏幕上的输出。通过输出点可以知道输出的某些数据内容</strong>。当然，实际上肯定不会这样简单，这么说只是为了帮助理解。在顶点着色器中的内建变量和片元着色器的内建变量是不相同的。着色器中的内建变量有很多，在此，我们只列出最常用的集中内建变量。</p>



<h2 id="顶点着色器的内建变量">顶点着色器的内建变量</h2>

<ol>
<li><p>输入变量：</p>

<ul><li>gl_Position：顶点坐标</li>
<li>gl_PointSize：点的大小，没有赋值则为默认值1，通常设置绘图为点绘制才有意义。</li></ul></li>
</ol>



<h2 id="片元着色器的内建变量">片元着色器的内建变量</h2>

<ol>
<li><p>输入变量</p>

<ul><li>gl_FragCoord：当前片元相对窗口位置所处的坐标。</li>
<li>gl_FragFacing：bool型，表示是否为属于光栅化生成此片元的对应图元的正面。</li></ul></li>
<li><p>输出变量</p>

<ul><li>gl_FragColor：当前片元颜色</li>
<li>gl_FragData：vec4类型的数组。向其写入的信息，供渲染管线的后继过程使用。</li></ul></li>
</ol>



<h1 id="常用内置函数">常用内置函数</h1>



<h2 id="常见函数">常见函数</h2>

<ul>
<li>radians(x)：角度转弧度</li>
<li>degrees(x)：弧度转角度</li>
<li>sin(x)：正弦函数，传入值为弧度。相同的还有cos余弦函数、tan正切函数、asin反正弦、acos反余弦、atan反正切</li>
<li>pow(x,y)：<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-13-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-163" style="width: 1.304em; display: inline-block;"><span style="display: inline-block; position: relative; width: 1.033em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(1.466em 1000em 2.442em -0.431em); top: -2.274em; left: 0.003em;"><span class="mrow" id="MathJax-Span-164"><span class="msubsup" id="MathJax-Span-165"><span style="display: inline-block; position: relative; width: 0.978em; height: 0px;"><span style="position: absolute; clip: rect(1.683em 1000em 2.442em -0.431em); top: -2.274em; left: 0.003em;"><span class="mi" id="MathJax-Span-166" style="font-family: MathJax_Math-italic;">x</span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span><span style="position: absolute; top: -2.653em; left: 0.545em;"><span class="mi" id="MathJax-Span-167" style="font-size: 70.7%; font-family: MathJax_Math-italic;">y<span style="display: inline-block; overflow: hidden; height: 1px; width: 0.003em;"></span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span></span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.003em; vertical-align: -0.063em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-13">x^y</script></li>
<li>exp(x)：<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-14-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-168" style="width: 1.141em; display: inline-block;"><span style="display: inline-block; position: relative; width: 0.924em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(1.466em 1000em 2.442em -0.431em); top: -2.274em; left: 0.003em;"><span class="mrow" id="MathJax-Span-169"><span class="msubsup" id="MathJax-Span-170"><span style="display: inline-block; position: relative; width: 0.87em; height: 0px;"><span style="position: absolute; clip: rect(1.683em 1000em 2.442em -0.431em); top: -2.274em; left: 0.003em;"><span class="mi" id="MathJax-Span-171" style="font-family: MathJax_Math-italic;">e</span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span><span style="position: absolute; top: -2.653em; left: 0.436em;"><span class="mi" id="MathJax-Span-172" style="font-size: 70.7%; font-family: MathJax_Math-italic;">x</span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span></span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.003em; vertical-align: -0.063em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-14">e^x</script></li>
<li>exp2(x)：<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-15-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-173" style="width: 1.195em; display: inline-block;"><span style="display: inline-block; position: relative; width: 0.978em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(1.412em 1000em 2.442em -0.431em); top: -2.274em; left: 0.003em;"><span class="mrow" id="MathJax-Span-174"><span class="msubsup" id="MathJax-Span-175"><span style="display: inline-block; position: relative; width: 0.924em; height: 0px;"><span style="position: absolute; clip: rect(1.466em 1000em 2.442em -0.431em); top: -2.274em; left: 0.003em;"><span class="mn" id="MathJax-Span-176" style="font-family: MathJax_Main;">2</span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span><span style="position: absolute; top: -2.653em; left: 0.491em;"><span class="mi" id="MathJax-Span-177" style="font-size: 70.7%; font-family: MathJax_Math-italic;">x</span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span></span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.003em; vertical-align: -0.063em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-15">2^x</script></li>
<li>log(x)：<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-16-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-178" style="width: 2.821em; display: inline-block;"><span style="display: inline-block; position: relative; width: 2.279em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(1.412em 1000em 2.713em -0.431em); top: -2.274em; left: 0.003em;"><span class="mrow" id="MathJax-Span-179"><span class="mi" id="MathJax-Span-180" style="font-family: MathJax_Math-italic;">l</span><span class="mi" id="MathJax-Span-181" style="font-family: MathJax_Math-italic;">o</span><span class="msubsup" id="MathJax-Span-182"><span style="display: inline-block; position: relative; width: 0.924em; height: 0px;"><span style="position: absolute; clip: rect(1.683em 1000em 2.659em -0.485em); top: -2.274em; left: 0.003em;"><span class="mi" id="MathJax-Span-183" style="font-family: MathJax_Math-italic;">g<span style="display: inline-block; overflow: hidden; height: 1px; width: 0.003em;"></span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span><span style="position: absolute; top: -2.057em; left: 0.545em;"><span class="mi" id="MathJax-Span-184" style="font-size: 70.7%; font-family: MathJax_Math-italic;">e</span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span></span><span class="mi" id="MathJax-Span-185" style="font-family: MathJax_Math-italic;">x</span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.27em; vertical-align: -0.397em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-16">log_e x</script></li>
<li>log2(x)：<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-17-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-186" style="width: 2.875em; display: inline-block;"><span style="display: inline-block; position: relative; width: 2.333em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(1.412em 1000em 2.659em -0.431em); top: -2.274em; left: 0.003em;"><span class="mrow" id="MathJax-Span-187"><span class="mi" id="MathJax-Span-188" style="font-family: MathJax_Math-italic;">l</span><span class="mi" id="MathJax-Span-189" style="font-family: MathJax_Math-italic;">o</span><span class="msubsup" id="MathJax-Span-190"><span style="display: inline-block; position: relative; width: 0.978em; height: 0px;"><span style="position: absolute; clip: rect(1.683em 1000em 2.659em -0.485em); top: -2.274em; left: 0.003em;"><span class="mi" id="MathJax-Span-191" style="font-family: MathJax_Math-italic;">g<span style="display: inline-block; overflow: hidden; height: 1px; width: 0.003em;"></span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span><span style="position: absolute; top: -2.057em; left: 0.545em;"><span class="mn" id="MathJax-Span-192" style="font-size: 70.7%; font-family: MathJax_Main;">2</span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span></span><span class="mi" id="MathJax-Span-193" style="font-family: MathJax_Math-italic;">x</span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.27em; vertical-align: -0.33em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-17">log_2 x</script></li>
<li>sqrt(x)：<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-18-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-194" style="width: 1.737em; display: inline-block;"><span style="display: inline-block; position: relative; width: 1.412em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(1.412em 1000em 2.713em -0.431em); top: -2.274em; left: 0.003em;"><span class="mrow" id="MathJax-Span-195"><span class="msqrt" id="MathJax-Span-196"><span style="display: inline-block; position: relative; width: 1.358em; height: 0px;"><span style="position: absolute; clip: rect(1.683em 1000em 2.442em -0.431em); top: -2.274em; left: 0.816em;"><span class="mrow" id="MathJax-Span-197"><span class="mi" id="MathJax-Span-198" style="font-family: MathJax_Math-italic;">x</span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span><span style="position: absolute; clip: rect(0.924em 1000em 1.304em -0.485em); top: -1.786em; left: 0.816em;"><span style="border-left-width: 0.545em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.25px; vertical-align: -0.051em;"></span><span style="display: inline-block; width: 0px; height: 1.087em;"></span></span><span style="position: absolute; clip: rect(3.038em 1000em 4.393em -0.431em); top: -3.954em; left: 0.003em;"><span style="font-family: MathJax_Main;">√</span><span style="display: inline-block; width: 0px; height: 4.014em;"></span></span></span></span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.337em; vertical-align: -0.397em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-18">\sqrt{x}</script></li>
<li>inversesqr(x)：<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-19-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-199" style="width: 1.683em; display: inline-block;"><span style="display: inline-block; position: relative; width: 1.358em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(3.201em 1000em 5.64em -0.485em); top: -4.008em; left: 0.003em;"><span class="mrow" id="MathJax-Span-200"><span style="display: inline-block; position: relative; width: 1.358em; height: 0px;"><span style="position: absolute; clip: rect(3.201em 1000em 4.176em -0.377em); top: -4.008em; left: 0.003em;"><span class="mn" id="MathJax-Span-201" style="font-family: MathJax_Main;">1</span><span style="display: inline-block; width: 0px; height: 4.014em;"></span></span><span style="position: absolute; clip: rect(3.146em 1000em 4.447em -0.485em); top: -2.816em; left: 0.003em;"><span class="mspace" id="MathJax-Span-202" style="height: 0.003em; vertical-align: 0.003em; width: 0.003em; display: inline-block; overflow: hidden;"></span><span class="msqrt" id="MathJax-Span-203"><span style="display: inline-block; position: relative; width: 1.358em; height: 0px;"><span style="position: absolute; clip: rect(1.683em 1000em 2.442em -0.431em); top: -2.274em; left: 0.816em;"><span class="mrow" id="MathJax-Span-204"><span class="mi" id="MathJax-Span-205" style="font-family: MathJax_Math-italic;">x</span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span><span style="position: absolute; clip: rect(0.924em 1000em 1.304em -0.485em); top: -1.786em; left: 0.816em;"><span style="border-left-width: 0.545em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.25px; vertical-align: -0.051em;"></span><span style="display: inline-block; width: 0px; height: 1.087em;"></span></span><span style="position: absolute; clip: rect(3.038em 1000em 4.393em -0.431em); top: -3.954em; left: 0.003em;"><span style="font-family: MathJax_Main;">√</span><span style="display: inline-block; width: 0px; height: 4.014em;"></span></span></span></span><span style="display: inline-block; width: 0px; height: 4.014em;"></span></span></span></span><span style="display: inline-block; width: 0px; height: 4.014em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 2.803em; vertical-align: -1.863em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-19">1\\\sqrt{x}</script></li>
<li>abs(x)：取x的绝对值</li>
<li>sign(x)：x&gt;0返回1.0，x&lt;0返回-1.0，否则返回0.0</li>
<li>ceil(x)：返回大于或者等于x的整数</li>
<li>floor(x)：返回小于或者等于x的整数</li>
<li>fract(x)：返回x-floor(x)的值</li>
<li>mod(x,y)：取模（求余）</li>
<li>min(x,y)：获取xy中小的那个</li>
<li>max(x,y)：获取xy中大的那个</li>
<li>mix(x,y,a)：返回<span class="MathJax_Preview"></span><span class="MathJax" id="MathJax-Element-20-Frame" role="textbox" aria-readonly="true"><nobr><span class="math" id="MathJax-Span-206" style="width: 9.434em; display: inline-block;"><span style="display: inline-block; position: relative; width: 7.645em; height: 0px; font-size: 123%;"><span style="position: absolute; clip: rect(1.358em 1000em 2.713em -0.431em); top: -2.274em; left: 0.003em;"><span class="mrow" id="MathJax-Span-207"><span class="mi" id="MathJax-Span-208" style="font-family: MathJax_Math-italic;">x</span><span class="mo" id="MathJax-Span-209" style="font-family: MathJax_Main; padding-left: 0.22em;">∗</span><span class="mo" id="MathJax-Span-210" style="font-family: MathJax_Main; padding-left: 0.22em;">(</span><span class="mn" id="MathJax-Span-211" style="font-family: MathJax_Main;">1</span><span class="mo" id="MathJax-Span-212" style="font-family: MathJax_Main; padding-left: 0.22em;">−</span><span class="mi" id="MathJax-Span-213" style="font-family: MathJax_Math-italic; padding-left: 0.22em;">a</span><span class="mo" id="MathJax-Span-214" style="font-family: MathJax_Main;">)</span><span class="mo" id="MathJax-Span-215" style="font-family: MathJax_Main; padding-left: 0.22em;">+</span><span class="mi" id="MathJax-Span-216" style="font-family: MathJax_Math-italic; padding-left: 0.22em;">y<span style="display: inline-block; overflow: hidden; height: 1px; width: 0.003em;"></span></span><span class="mo" id="MathJax-Span-217" style="font-family: MathJax_Main; padding-left: 0.22em;">∗</span><span class="mi" id="MathJax-Span-218" style="font-family: MathJax_Math-italic; padding-left: 0.22em;">a</span></span><span style="display: inline-block; width: 0px; height: 2.279em;"></span></span></span><span style="border-left-width: 0.003em; border-left-style: solid; display: inline-block; overflow: hidden; width: 0px; height: 1.337em; vertical-align: -0.397em;"></span></span></nobr></span><script type="math/tex" id="MathJax-Element-20">x*(1-a)+y*a</script></li>
<li>step(x,a)：x&lt; a返回0.0，否则返回1.0</li>
<li>smoothstep(x,y,a)：a &lt; x返回0.0，a&gt;y返回1.0，否则返回0.0-1.0之间平滑的Hermite插值。</li>
<li>dFdx(p)：p在x方向上的偏导数</li>
<li>dFdy(p)：p在y方向上的偏导数</li>
<li>fwidth(p)：p在x和y方向上的偏导数的绝对值之和</li>
</ul>



<h2 id="几何函数">几何函数</h2>

<ul>
<li>length(x)：计算向量x的长度</li>
<li>distance(x,y)：返回向量xy之间的距离</li>
<li>dot(x,y)：返回向量xy的点积</li>
<li>cross(x,y)：返回向量xy的差积</li>
<li>normalize(x)：返回与x向量方向相同，长度为1的向量</li>
</ul>



<h2 id="矩阵函数">矩阵函数</h2>

<ul>
<li>matrixCompMult(x,y)：将矩阵相乘</li>
<li>lessThan(x,y)：返回向量xy的各个分量执行x&lt; y的结果，类似的有greaterThan,equal,notEqual</li>
<li>lessThanEqual(x,y)：返回向量xy的各个分量执行x&lt;= y的结果，类似的有类似的有greaterThanEqual</li>
<li>any(bvec x)：x有一个元素为true，则为true</li>
<li>all(bvec x)：x所有元素为true，则返回true，否则返回false</li>
<li>not(bvec x)：x所有分量执行逻辑非运算</li>
</ul>



<h2 id="纹理采样函数">纹理采样函数</h2>

<p>纹理采样函数有texture2D、texture2DProj、texture2DLod、texture2DProjLod、textureCube、textureCubeLod及texture3D、texture3DProj、texture3DLod、texture3DProjLod等。</p>

<ul>
<li>texture表示纹理采样，2D表示对2D纹理采样，3D表示对3D纹理采样</li>
<li>Lod后缀，只适用于顶点着色器采样</li>
<li>Proj表示纹理坐标st会除以q</li>
</ul>

<p>纹理采样函数中，3D在OpenGLES2.0并不是绝对支持。我们再次暂时不管3D纹理采样函数。重点只对texture2D函数进行说明。texture2D拥有三个参数，第一个参数表示纹理采样器。第二个参数表示纹理坐标，可以是二维、三维、或者四维。第三个参数加入后只能在片元着色器中调用，且只对采样器为mipmap类型纹理时有效。</p>