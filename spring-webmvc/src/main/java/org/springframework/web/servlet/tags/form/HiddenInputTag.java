package org.springframework.web.servlet.tags.form;

import javax.servlet.jsp.JspException;

/**
 * The {@code <hidden>} tag renders an HTML 'input' tag with type 'hidden' using
 * the bound value.
 *
 * <p>Example (binding to 'name' property of form backing object):
 * <pre class="code">
 * &lt;form:hidden path=&quot;name&quot;/&gt;
 * </pre>
 *
 * <p>
 * <table>
 * <caption>Attribute Summary</caption>
 * <thead>
 * <tr>
 * <th class="colFirst">Attribute</th>
 * <th class="colOne">Required?</th>
 * <th class="colOne">Runtime Expression?</th>
 * <th class="colLast">Description</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="altColor">
 * <td><p>htmlEscape</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>Enable/disable HTML escaping of rendered values.</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>id</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>path</p></td>
 * <td><p>true</p></td>
 * <td><p>true</p></td>
 * <td><p>Path to property for data binding</p></td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @since 2.0
 */
@SuppressWarnings("serial")
public class HiddenInputTag extends AbstractHtmlElementTag {

	/**
	 * The name of the '{@code disabled}' attribute.
	 */
	public static final String DISABLED_ATTRIBUTE = "disabled";

	private boolean disabled;


	/**
	 * Set the value of the '{@code disabled}' attribute.
	 * May be a runtime expression.
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Get the value of the '{@code disabled}' attribute.
	 */
	public boolean isDisabled() {
		return this.disabled;
	}


	/**
	 * Flags "type" as an illegal dynamic attribute.
	 */
	@Override
	protected boolean isValidDynamicAttribute(String localName, Object value) {
		return !"type".equals(localName);
	}

	/**
	 * Writes the HTML '{@code input}' tag to the supplied {@link TagWriter} including the
	 * databound value.
	 *
	 * @see #writeDefaultAttributes(TagWriter)
	 * @see #getBoundValue()
	 */
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("input");
		writeDefaultAttributes(tagWriter);
		tagWriter.writeAttribute("type", "hidden");
		if (isDisabled()) {
			tagWriter.writeAttribute(DISABLED_ATTRIBUTE, "disabled");
		}
		String value = getDisplayString(getBoundValue(), getPropertyEditor());
		tagWriter.writeAttribute("value", processFieldValue(getName(), value, "hidden"));
		tagWriter.endTag();
		return SKIP_BODY;
	}

}
